package com.ucv.codetech.facade;

import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.controller.model.input.CommentDto;
import com.ucv.codetech.controller.model.input.CourseDto;
import com.ucv.codetech.controller.model.input.LectureDto;
import com.ucv.codetech.controller.model.input.QuizDto;
import com.ucv.codetech.controller.model.output.PreviewCourseDto;
import com.ucv.codetech.controller.model.output.PreviewFullCourseDto;
import com.ucv.codetech.facade.converter.CommentConverter;
import com.ucv.codetech.facade.converter.CourseConverter;
import com.ucv.codetech.facade.converter.LectureConverter;
import com.ucv.codetech.facade.converter.QuizConverter;
import com.ucv.codetech.model.*;
import com.ucv.codetech.service.*;
import com.ucv.codetech.service.file.FileService;
import com.ucv.codetech.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.ucv.codetech.StartupComponent.Facade;

@Facade
@AllArgsConstructor
public class CourseFacade {

    private final LectureService lectureService;
    private final CommentService commentService;
    private final CourseService courseService;
    private final CategoryService categoryService;
    private final FileService fileService;
    private final CourseConverter courseConverter;
    private final LectureConverter lectureConverter;
    private final QuizConverter quizConverter;
    private final CommentConverter commentConverter;
    private final UserService userService;
    private final QuizService quizService;

    @Transactional
    public Long createCourse(CourseDto courseDto, String username) {
        try {
            if (courseService.courseExistsByName(courseDto.getName())) {
                throw new AppException("The course already exists with this name!",
                        HttpStatus.BAD_REQUEST);
            }
            Instructor instructor = userService.getInstructor(username);
            Category category = categoryService.findById(courseDto.getCategoryId());
            Course course = courseConverter.dtoToEntity(courseDto);
            instructor.addCourse(course);
            course.setInstructor(instructor);
            course.setCategory(category);
            String folderName = fileService.createCourseFolder(courseDto.getName());
            course.setFolderName(folderName);
            userService.saveInstructor(instructor);
            return courseService.saveOrUpdate(course).getId();
        } catch (IOException ioException) {
            throw new AppException("Error occurred while creating the course's folder", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void addCourseCover(MultipartFile cover, Long id) {
        if (courseService.containsCourseCover(id)) {
            throw new AppException("The course with id " + id + " already has a cover", HttpStatus.BAD_REQUEST);
        }
        Course course = courseService.findById(id);
        try {
            String filename = fileService.moveFile(cover, course.getFolderName());
            course.setCoverImageName(filename);
            courseService.saveOrUpdate(course);
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

    public PreviewFullCourseDto getById(Long id) {
        Course course = courseService.findById(id);
        return courseConverter.entityToFullDisplayCourseDto(course);
    }

    public List<PreviewCourseDto> getAll(String name) {
        List<Course> usersCourses = userService.getStudent(name).getEnrolledCourses()
                .stream()
                .map(EnrolledCourse::getCourse)
                .collect(Collectors.toList());
        List<Course> courses = courseService.getAll();
        return courseConverter.courseListToDisplayCourseDtoList(courses, usersCourses);
    }

    @Transactional
    public Long createLecture(Long courseId, LectureDto lectureDto) {
        try {
            if (lectureService.lectureExistsInCourse(lectureDto.getName(), courseId)) {
                throw new AppException("A lecture with the name " + lectureDto.getName() + " already exists in the course", HttpStatus.BAD_REQUEST);
            }
            Course course = courseService.findById(courseId);
            String lectureVideoName = fileService.moveFile(lectureDto.getLectureVideo(), course.getFolderName());
            Lecture lecture = lectureConverter.dtoToEntity(lectureDto);
            lecture.setLectureVideoName(lectureVideoName);
            course.addLecture(lecture);
            courseService.saveOrUpdate(course);
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
            List<String> filesToDelete = courseService.delete(id);
            fileService.deleteCourseFilesData(filesToDelete, courseFolderName);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Transactional
    public Long addComment(Long id, CommentDto commentDto, String name) {
        Student student = userService.getStudent(name);
        Course course = courseService.findById(id);
        Comment comment = commentConverter.dtoToEntity(commentDto);
        comment.setComment(student, course);
        return commentService.saveOrUpdate(comment);
    }

    @Transactional
    public Long createQuiz(Long id, QuizDto quizDto, String username) {
        Instructor instructor = userService.getInstructor(username);
        if (courseService.hasQuiz(id)) {
            throw new AppException("The course already has an associated quiz", HttpStatus.BAD_REQUEST);
        }
        Course course = courseService.findById(id);
        Quiz quiz = quizConverter.quizDtoToEntity(quizDto);
        course.setQuiz(quiz);
        instructor.addQuiz(quiz);
        quizService.saveOrUpdate(quiz);
        return quiz.getId();
    }

    @Transactional
    public void enrollToCourse(Long id, String username) {
        Course course = courseService.findById(id);
        Student student = userService.getStudent(username);
        if (course.containsStudent(student)) {
            throw new AppException("Student " + username + " is already enrolled in the course " + course.getName(), HttpStatus.BAD_REQUEST);
        }
        course.enrollStudent(student, courseToEnrolledCourse(course, student));
        userService.saveStudent(student);
    }

    @Transactional
    public void deleteCourseCover(Long id) {
        try {
            String courseFolderName = courseService.getCourseFolderName(id);
            String coverImage = courseService.getCourseCoverById(id).getCoverImageName();
            fileService.deleteFile(courseFolderName, coverImage);
        } catch (IOException ioException) {
            throw new AppException("An error occurred while deleting the cover image of the course with id " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
