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
import com.ucv.codetech.service.file.FileService;
import com.ucv.codetech.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

import static com.ucv.codetech.StartupComponent.Facade;

@Facade
@AllArgsConstructor
public class CourseFacade {

    private final CourseService courseService;
    private final CategoryService categoryService;
    private final FileService fileService;
    private final CourseConverter courseConverter;
    private final LectureConverter lectureConverter;
    private final QuizConverter quizConverter;
    private final StudentRepository studentRepository;
    private final CommentConverter commentConverter;
    private final UserService userService;

    @Transactional
    public Long createCourse(CourseDto courseDto) {
        try {
            if (courseService.courseExistsByName(courseDto.getName())) {
                throw new AppException("The course already exists with this name!",
                        HttpStatus.BAD_REQUEST);
            }
            Instructor instructor = userService.getInstructor(courseDto.getInstructorName());
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
    public void createLecture(Long courseId, LectureDto lectureDto) {
        try {
            Course course = courseService.getById(courseId);
            String lectureVideoName = fileService.moveFile(lectureDto.getLectureVideo(), course.getFolderName());
            Lecture lecture = lectureConverter.lectureDtoToLecture(lectureDto);
            lecture.setLectureVideoName(lectureVideoName);
            lecture.setCourse(course);
            course.addLecture(lecture);
            courseService.createOrUpdate(course);
        } catch (IOException e) {
            e.printStackTrace();
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
    public void addComment(Long id, CommentDto commentDto) {
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
    }

    @Transactional
    public void createQuiz(Long id, QuizDto quizDto) {
        if(courseService.hasQuiz(id)) {
            throw new AppException("The course already has an associated quiz", HttpStatus.BAD_REQUEST);
        }
        Course course = courseService.getById(id);
        Quiz quiz = quizConverter.quizDtoToEntity(quizDto);
        course.setQuiz(quiz);
        quiz.setCourse(course);
        courseService.createOrUpdate(course);
    }
}
