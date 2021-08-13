package com.ucv.codetech.facade;

import com.ucv.codetech.StartupComponent.Facade;
import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.controller.model.input.InstructorDto;
import com.ucv.codetech.controller.model.input.StudentDto;
import com.ucv.codetech.controller.model.output.StudentCertificationDisplayDto;
import com.ucv.codetech.controller.model.output.StudentCourseDisplayDto;
import com.ucv.codetech.controller.model.output.StudentFullCourseDisplayDto;
import com.ucv.codetech.facade.converter.AppUserConverter;
import com.ucv.codetech.facade.converter.CertificationConverter;
import com.ucv.codetech.facade.converter.EnrolledCourseConverter;
import com.ucv.codetech.model.*;
import com.ucv.codetech.service.EnrolledCourseService;
import com.ucv.codetech.service.LectureWrapperService;
import com.ucv.codetech.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Facade
@AllArgsConstructor
public class UserFacade {

    private final UserService userService;
    private final CertificationConverter certificationConverter;
    private final EnrolledCourseService enrolledCourseService;
    private final PasswordEncoder passwordEncoder;
    private final AppUserConverter appUserConverter;
    private final LectureWrapperService lectureWrapperService;
    private final EnrolledCourseConverter enrolledCourseConverter;

    @Transactional
    public Long registerStudent(StudentDto studentDto) {
        validateUserName(studentDto.getUsername());
        validateEmail(studentDto.getEmail());
        Student student = appUserConverter.dtoToEntity(studentDto);
        student.setPassword(passwordEncoder.encode(studentDto.getPassword()));
        return userService.saveStudent(student).getId();
    }

    @Transactional
    public Long registerInstructor(InstructorDto instructorDto) {
        validateUserName(instructorDto.getUsername());
        validateEmail(instructorDto.getEmail());
        Instructor instructor = appUserConverter.dtoToEntity(instructorDto);
        instructor.setPassword(passwordEncoder.encode(instructorDto.getPassword()));
        return userService.saveInstructor(instructor).getId();
    }

    public AppUser getAppUser(String username) {
        return userService.getAppUser(username);
    }

    public StudentFullCourseDisplayDto getEnrolledCourse(String username, Long id) {
        EnrolledCourse enrolledCourse = enrolledCourseService.findById(id, username);
        return enrolledCourseConverter.entityToStudentFullCourseDisplayDto(enrolledCourse);
    }

    public List<StudentCourseDisplayDto> getEnrolledCourses(String username) {
        Student student = userService.getStudent(username);
        List<EnrolledCourse> enrolledCourses = student.getEnrolledCourses();
        return enrolledCourseConverter.entitiesToStudentCourseDisplayDtos(enrolledCourses);
    }

    @Transactional
    public void completeLecture(Long id) {
        LectureWrapper lectureWrapper = lectureWrapperService.findById(id);
        lectureWrapper.completeLecture();
        lectureWrapperService.saveOrUpdate(lectureWrapper);
    }

    public List<StudentCertificationDisplayDto> getCertifications(String name) {
        Student student = userService.getStudent(name);
        return certificationConverter.entitiesToStudentCertificationDisplayDtos(student.getCertifications());
    }

    private void validateUserName(String username) {
        if (userService.userExistsByName(username)) {
            throw new AppException("The user with the name " + username + " already exists", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateEmail(String email) {
        if (userService.userExistsByEmail(email)) {
            throw new AppException("The user with the email " + email + " already exists", HttpStatus.BAD_REQUEST);
        }
    }
}
