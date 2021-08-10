package com.ucv.codetech.repository;

import com.ucv.codetech.model.EnrolledCourse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrolledCourseRepository extends JpaRepository<EnrolledCourse, Long> {
}
