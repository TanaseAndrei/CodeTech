package com.ucv.codetech.facade;

import com.ucv.codetech.StartupComponent.Facade;
import com.ucv.codetech.controller.model.input.InstructorDto;
import com.ucv.codetech.controller.model.input.StudentDto;
import com.ucv.codetech.facade.converter.AppUserConverter;
import com.ucv.codetech.model.AppUser;
import com.ucv.codetech.model.Instructor;
import com.ucv.codetech.model.Student;
import com.ucv.codetech.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Facade
@AllArgsConstructor
public class UserFacade {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AppUserConverter appUserConverter;

    public Long registerStudent(StudentDto studentDto) {
        Student student = appUserConverter.dtoToEntity(studentDto);
        student.setPassword(passwordEncoder.encode(studentDto.getPassword()));
        return userService.saveStudent(student).getId();
    }

    public Long registerInstructor(InstructorDto instructorDto) {
        Instructor instructor = appUserConverter.dtoToEntity(instructorDto);
        instructor.setPassword(passwordEncoder.encode(instructorDto.getPassword()));
        return userService.saveInstructor(instructor).getId();
    }

    public AppUser getAppUser(String username) {
        return userService.getAppUser(username);
    }
}
