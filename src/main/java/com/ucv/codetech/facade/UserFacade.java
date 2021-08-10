package com.ucv.codetech.facade;

import com.ucv.codetech.StartupComponent.Facade;
import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.controller.model.input.InstructorDto;
import com.ucv.codetech.controller.model.input.StudentDto;
import com.ucv.codetech.facade.converter.AppUserConverter;
import com.ucv.codetech.model.AppUser;
import com.ucv.codetech.model.EnrolledCourse;
import com.ucv.codetech.model.Instructor;
import com.ucv.codetech.model.Student;
import com.ucv.codetech.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Facade
@AllArgsConstructor
public class UserFacade {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AppUserConverter appUserConverter;

    public Long registerStudent(StudentDto studentDto) {
        validateUserName(studentDto.getUsername());
        validateEmail(studentDto.getEmail());
        Student student = appUserConverter.dtoToEntity(studentDto);
        student.setPassword(passwordEncoder.encode(studentDto.getPassword()));
        return userService.saveStudent(student).getId();
    }

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

    public List<EnrolledCourse> getEnrolledCourses(String username) {
        Student student = userService.getStudent(username);
        return student.getEnrolledCourses();
    }

    private void validateUserName(String username) {
        if(userService.userExistsByName(username)) {
            throw new AppException("The user with the name " + username + " already exists", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateEmail(String email) {
        if(userService.userExistsByEmail(email)) {
            throw new AppException("The user with the email " + email + " already exists", HttpStatus.BAD_REQUEST);
        }
    }
}
