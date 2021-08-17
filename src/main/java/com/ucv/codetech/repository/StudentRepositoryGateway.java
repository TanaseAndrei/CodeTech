package com.ucv.codetech.repository;

import com.ucv.codetech.model.Student;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class StudentRepositoryGateway {

    private final StudentRepository studentRepository;

    public Optional<Student> findByUsername(String name) {
        return studentRepository.findByUsername(name);
    }

    public Student save(Student student) {
        return studentRepository.save(student);
    }
}
