package com.ucv.codetech.facade.converter;

import com.ucv.codetech.controller.model.input.AppUserDto;
import com.ucv.codetech.model.Instructor;
import com.ucv.codetech.model.Role;
import com.ucv.codetech.model.Student;
import org.springframework.stereotype.Component;

@Component
public class AppUserConverter {

    public Student dtoToStudent(AppUserDto appUserDto) {
        Student student = new Student();
        student.setEmail(appUserDto.getEmail());
        student.setRole(Role.getByName(appUserDto.getRoleDto().name()));
        student.setUsername(appUserDto.getUsername());
        return student;
    }

    public Instructor dtoToInstructor(AppUserDto appUserDto) {
        Instructor instructor = new Instructor();
        instructor.setEmail(appUserDto.getEmail());
        instructor.setRole(Role.getByName(appUserDto.getRoleDto().name()));
        instructor.setUsername(appUserDto.getUsername());
        return instructor;
    }
}
