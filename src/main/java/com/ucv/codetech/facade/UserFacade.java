package com.ucv.codetech.facade;

import com.ucv.codetech.CodeTechApplication.Facade;
import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.controller.model.input.AppUserDto;
import com.ucv.codetech.controller.model.input.RoleDto;
import com.ucv.codetech.controller.model.output.*;
import com.ucv.codetech.facade.converter.AppUserConverter;
import com.ucv.codetech.facade.converter.CertificationConverter;
import com.ucv.codetech.facade.converter.CourseConverter;
import com.ucv.codetech.facade.converter.QuizConverter;
import com.ucv.codetech.model.*;
import com.ucv.codetech.service.*;
import com.ucv.codetech.service.model.RegisterEmail;
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
    private final EmailRestClientService emailRestClientService;
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
    public Long registerUser(AppUserDto appUserDto) {
        log.info("Registering new {} with name {}", appUserDto.getRoleDto(), appUserDto.getUsername());
        validate(appUserDto.getUsername(), appUserDto.getEmail());
        Long userId = saveUser(appUserDto);
        emailRestClientService.sendEmail(getRegisterEmail(appUserDto));
        log.info("Registered the new {} with name {}", appUserDto.getRoleDto(), appUserDto.getUsername());
        return userId;
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

    private RegisterEmail getRegisterEmail(AppUserDto appUserDto) {
        return new RegisterEmail(appUserDto.getUsername(), appUserDto.getEmail(),
                Role.getByName(appUserDto.getRoleDto().name()));
    }

    private Long saveUser(AppUserDto appUserDto) {
        RoleDto roleDto = appUserDto.getRoleDto();
        switch(roleDto) {
            case INSTRUCTOR:
                return saveInstructor(appUserDto);
            case STUDENT:
                return saveStudent(appUserDto);
            default:
                throw new AppException("Unexpected value: " + roleDto, HttpStatus.BAD_REQUEST);
        }
    }

    private Long saveStudent(AppUserDto appUserDto) {
        Student student = appUserConverter.dtoToStudent(appUserDto);
        student.setPassword(passwordEncoder.encode(appUserDto.getPassword()));
        return userService.saveStudent(student).getId();
    }

    private Long saveInstructor(AppUserDto appUserDto) {
        Instructor instructor = appUserConverter.dtoToInstructor(appUserDto);
        instructor.setPassword(passwordEncoder.encode(appUserDto.getPassword()));
        return userService.saveInstructor(instructor).getId();
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
