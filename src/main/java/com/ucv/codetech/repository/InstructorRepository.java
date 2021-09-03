package com.ucv.codetech.repository;

import com.ucv.codetech.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface InstructorRepository extends JpaRepository<Instructor, Long> {
    Optional<Instructor> findByUsername(String name);
}
