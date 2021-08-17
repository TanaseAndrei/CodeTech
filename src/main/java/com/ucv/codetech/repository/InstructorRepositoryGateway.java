package com.ucv.codetech.repository;

import com.ucv.codetech.model.Instructor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class InstructorRepositoryGateway {

    private final InstructorRepository instructorRepository;

    public Optional<Instructor> findByUsername(String name) {
        return instructorRepository.findByUsername(name);
    }

    public Instructor save(Instructor instructor) {
        return instructorRepository.save(instructor);
    }
}
