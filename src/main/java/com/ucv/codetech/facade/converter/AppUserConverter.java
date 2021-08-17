package com.ucv.codetech.facade.converter;

import com.ucv.codetech.controller.model.input.InstructorDto;
import com.ucv.codetech.controller.model.input.StudentDto;
import com.ucv.codetech.model.Instructor;
import com.ucv.codetech.model.Role;
import com.ucv.codetech.model.Student;
import org.springframework.stereotype.Component;

@Component
public class AppUserConverter {

    public Student dtoToEntity(StudentDto studentDto) {
        Student student = new Student();
        student.setEmail(studentDto.getEmail());
        student.setRole(Role.getByName(studentDto.getRoleDto().name()));
        student.setUsername(studentDto.getUsername());
        return student;
    }

    public Instructor dtoToEntity(InstructorDto instructorDto) {
        Instructor instructor = new Instructor();
        instructor.setEmail(instructorDto.getEmail());
        instructor.setRole(Role.getByName(instructorDto.getRoleDto().name()));
        instructor.setUsername(instructorDto.getUsername());
        return instructor;
    }
}
