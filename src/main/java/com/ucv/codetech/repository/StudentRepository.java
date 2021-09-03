package com.ucv.codetech.repository;

import com.ucv.codetech.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByUsername(String name);
}
