package com.ucv.codetech.repository;

import com.ucv.codetech.model.EnrolledCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnrolledCourseRepository extends JpaRepository<EnrolledCourse, Long> {

    Optional<EnrolledCourse> findByIdAndStudentUsername(Long id, String username);
}
