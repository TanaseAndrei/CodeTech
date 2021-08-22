package com.ucv.codetech.facade;

import com.ucv.codetech.StartupComponent.Facade;
import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.controller.model.input.InstructorDto;
import com.ucv.codetech.controller.model.input.StudentDto;
import com.ucv.codetech.controller.model.output.*;
import com.ucv.codetech.facade.converter.*;
import com.ucv.codetech.model.*;
import com.ucv.codetech.service.CourseService;
import com.ucv.codetech.service.EnrolledCourseService;
import com.ucv.codetech.service.LectureWrapperService;
import com.ucv.codetech.service.QuizService;
import com.ucv.codetech.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Facade
@AllArgsConstructor
@Slf4j
public class UserFacade {

    private final UserService userService;
    private final CertificationConverter certificationConverter;
    private final EnrolledCourseService enrolledCourseService;
    private final PasswordEncoder passwordEncoder;
    private final AppUserConverter appUserConverter;
    private final LectureWrapperService lectureWrapperService;
    private final CourseService courseService;
    private final CourseConverter courseConverter;
    private final QuizService quizService;
    private final QuizConverter quizConverter;

    @Transactional
    public Long registerStudent(StudentDto studentDto) {
        log.info("Registering new student with name {}", studentDto.getUsername());
        validate(studentDto.getUsername(), studentDto.getEmail());
        Student student = appUserConverter.dtoToEntity(studentDto);
        student.setPassword(passwordEncoder.encode(studentDto.getPassword()));
        log.info("Registered the student with name {}", studentDto.getUsername());
        return userService.saveStudent(student).getId();
    }

    @Transactional
    public Long registerInstructor(InstructorDto instructorDto) {
        log.info("Registering new instructor with name {}", instructorDto.getUsername());
        validate(instructorDto.getUsername(), instructorDto.getEmail());
        Instructor instructor = appUserConverter.dtoToEntity(instructorDto);
        instructor.setPassword(passwordEncoder.encode(instructorDto.getPassword()));
        log.info("Registered the instructor with name {}", instructorDto.getUsername());
        return userService.saveInstructor(instructor).getId();
    }

    public AppUser getAppUser(String username) {
        log.info("Getting application user with name {}", username);
        return userService.getAppUser(username);
    }

    public StudentFullCourseDisplayDto getEnrolledCourse(String username, Long id) {
        log.info("Getting full enrolled course {} of student {}", id, username);
        return courseConverter.entityToStudentFullCourseDisplayDto(enrolledCourseService.findById(id, username));
    }

    public List<StudentPreviewCourseDisplayDto> getEnrolledCourses(String username) {
        log.info("Getting the preview courses of student {}", username);
        Student student = userService.getStudent(username);
        return courseConverter.entitiesToStudentCourseDisplayDtos(student.getEnrolledCourses());
    }

    @Transactional
    public void completeLecture(Long id) {
        LectureWrapper lectureWrapper = lectureWrapperService.findById(id);
        lectureWrapper.completeLecture();
        lectureWrapperService.saveOrUpdate(lectureWrapper);
        log.info("Completed the lecture with id {}", id);
    }

    public List<StudentCertificationDisplayDto> getCertifications(String username) {
        log.info("Getting the certifications of student {}", username);
        Student student = userService.getStudent(username);
        return certificationConverter.entitiesToStudentCertificationDisplayDtos(student.getCertifications());
    }

    public List<InstructorPreviewCourseDisplayDto> getInstructorsCourses(String username) {
        log.info("Getting the preview courses of instructor {}", username);
        Instructor instructor = userService.getInstructor(username);
        return courseConverter.entitiesToInstructorCourseDisplayDtos(instructor.getCourses());
    }

    public InstructorFullCourseDisplayDto getInstructorCourse(String username, Long courseId) {
        log.info("Getting the course {} of the instructor {}", courseId, username);
        Course course = courseService.findByIdAndUsername(courseId, username);
        return courseConverter.entityToInstructorFullCourseDisplayDto(course);
    }

    public List<InstructorPreviewQuizDto> getQuizzes(String username) {
        log.info("Getting all quizzes of the instructor {}", username);
        List<Quiz> quizzes = quizService.findAllByInstructorName(username);
        return quizConverter.entitiesToInstructorPreviewQuizDto(quizzes);
    }

    private void validate(String username, String email) {
        validateUserName(username);
        validateEmail(email);
    }

    private void validateUserName(String username) {
        if (userService.userExistsByName(username)) {
            log.warn("Application user with name {} already exists", username);
            throw new AppException("The user with the name " + username + " already exists", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateEmail(String email) {
        if (userService.userExistsByEmail(email)) {
            log.warn("Application user with email {} already exists", email);
            throw new AppException("The user with the email " + email + " already exists", HttpStatus.BAD_REQUEST);
        }
    }
}
