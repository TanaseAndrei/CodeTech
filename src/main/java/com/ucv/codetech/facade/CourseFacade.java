package com.ucv.codetech.facade;

import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.controller.model.input.CommentDto;
import com.ucv.codetech.controller.model.input.CourseDto;
import com.ucv.codetech.controller.model.input.LectureDto;
import com.ucv.codetech.controller.model.input.QuizDto;
import com.ucv.codetech.controller.model.output.DisplayCourseDto;
import com.ucv.codetech.controller.model.output.FullDisplayCourseDto;
import com.ucv.codetech.facade.converter.CommentConverter;
import com.ucv.codetech.facade.converter.CourseConverter;
import com.ucv.codetech.facade.converter.LectureConverter;
import com.ucv.codetech.facade.converter.QuizConverter;
import com.ucv.codetech.model.*;
import com.ucv.codetech.repository.StudentRepository;
import com.ucv.codetech.service.CategoryService;
import com.ucv.codetech.service.CourseService;
import com.ucv.codetech.service.EnrolledCourseService;
import com.ucv.codetech.service.LectureService;
import com.ucv.codetech.service.file.FileService;
import com.ucv.codetech.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import static com.ucv.codetech.StartupComponent.Facade;

@Facade
@AllArgsConstructor
public class CourseFacade {

    private final LectureService lectureService;
    private final CourseService courseService;
    private final EnrolledCourseService enrolledCourseService;
    private final CategoryService categoryService;
    private final FileService fileService;
    private final CourseConverter courseConverter;
    private final LectureConverter lectureConverter;
    private final QuizConverter quizConverter;
    private final StudentRepository studentRepository;
    private final CommentConverter commentConverter;
    private final UserService userService;

    @Transactional
    public Long createCourse(CourseDto courseDto, Principal principal) {
        try {
            if (courseService.courseExistsByName(courseDto.getName())) {
                throw new AppException("The course already exists with this name!",
                        HttpStatus.BAD_REQUEST);
            }
            Instructor instructor = userService.getInstructor(principal.getName());
            Category category = categoryService.findById(courseDto.getCategoryId());
            Course course = courseConverter.courseDtoToCourse(courseDto);
            instructor.addCourse(course);
            course.setInstructor(instructor);
            course.setCategory(category);
            String folderName = fileService.createCourseFolder(courseDto.getName());
            course.setFolderName(folderName);
            userService.saveInstructor(instructor);
            return courseService.createOrUpdate(course).getId();
        } catch (IOException ioException) {
            throw new AppException("Error occurred while creating the course's folder", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void addCourseCover(MultipartFile cover, Long id) {
        try {
            Course course = courseService.getById(id);
            String filename = fileService.moveFile(cover, course.getFolderName());
            course.setCoverImageName(filename);
            courseService.createOrUpdate(course);
        } catch (IOException ioException) {
            throw new AppException("Error occurred while adding the course cover", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void enableCourse(Long id) {
        courseService.enableCourse(id);
    }

    @Transactional
    public void disableCourse(Long id) {
        courseService.disableCourse(id);
    }

    public FullDisplayCourseDto getById(Long id) {
        Course course = courseService.getById(id);
        return courseConverter.courseToFullDisplayCourseDto(course);
    }

    public List<DisplayCourseDto> getAll() {
        List<Course> courses = courseService.getAll();
        return courseConverter.courseListToDisplayCourseDtoList(courses);
    }

    @Transactional
    public Long createLecture(Long courseId, LectureDto lectureDto) {
        try {
            if(lectureService.lectureExistsInCourse(lectureDto.getName(), courseId)) {
                throw new AppException("A lecture with the name " + lectureDto.getName() + " already exists in the course", HttpStatus.BAD_REQUEST);
            }
            Course course = courseService.getById(courseId);
            String lectureVideoName = fileService.moveFile(lectureDto.getLectureVideo(), course.getFolderName());
            Lecture lecture = lectureConverter.lectureDtoToLecture(lectureDto);
            lecture.setLectureVideoName(lectureVideoName);
            lecture.setCourse(course);
            course.addLecture(lecture);
            courseService.createOrUpdate(course);
            return lecture.getId();
        } catch (IOException ioException) {
            throw new AppException("Something went wrong while creating the video to the course with id: " + courseId,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void deleteCourse(Long id) {
        try {
            String courseFolderName = courseService.getCourseFolderName(id);
            List<String> filesToDelete = courseService.deleteCourse(id);
            fileService.deleteCourseFilesData(filesToDelete, courseFolderName);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Transactional
    public Long addComment(Long id, CommentDto commentDto) {
        Course course = courseService.getById(id);
        Student student = studentRepository.findById(commentDto.getStudentId())
                .orElseThrow(() -> new AppException("The student with the id " + commentDto.getStudentId() + " does not exist", HttpStatus.NOT_FOUND));
        Comment comment = commentConverter.dtoToEntity(commentDto);
        comment.setCourse(course);
        comment.setStudent(student);
        course.addComment(comment);
        student.addComment(comment);
        courseService.createOrUpdate(course);
        studentRepository.save(student);
        return comment.getId();
    }

    @Transactional
    public Long createQuiz(Long id, QuizDto quizDto) {
        if(courseService.hasQuiz(id)) {
            throw new AppException("The course already has an associated quiz", HttpStatus.BAD_REQUEST);
        }
        Course course = courseService.getById(id);
        Quiz quiz = quizConverter.quizDtoToEntity(quizDto);
        course.setQuiz(quiz);
        quiz.setCourse(course);
        courseService.createOrUpdate(course);
        return quiz.getId();
    }

    @Transactional
    public void enrollToCourse(Long id, String username) {
        Course course = courseService.getById(id);
        Student student = userService.getStudent(username);
        if(course.getEnrolledStudents().contains(student)) {
            throw new AppException("Student " + username + " is already enrolled in the course " + course.getName(), HttpStatus.BAD_REQUEST);
        }
        student.enrollCourse(courseToEnrolledCourse(course, student));
        userService.saveStudent(student);
    }

    private EnrolledCourse courseToEnrolledCourse(Course course, Student student) {
        EnrolledCourse enrolledCourse = new EnrolledCourse();
        enrolledCourse.setCourse(course);
        enrolledCourse.setStudent(student);
        List<LectureWrapper> lectureWrappers = lecturesToLectureWrappers(course.getLectures());
        lectureWrappers.forEach(enrolledCourse::addLectureWrapper);
        enrolledCourse.setLectureWrappers(lectureWrappers);
        return enrolledCourse;
    }

    private List<LectureWrapper> lecturesToLectureWrappers(List<Lecture> lectures) {
        return lectures
                .stream()
                .map(this::lectureToLectureWrapper)
                .collect(Collectors.toList());
    }

    private LectureWrapper lectureToLectureWrapper(Lecture lecture) {
        LectureWrapper lectureWrapper = new LectureWrapper();
        lectureWrapper.setLecture(lecture);
        return lectureWrapper;
    }
}
