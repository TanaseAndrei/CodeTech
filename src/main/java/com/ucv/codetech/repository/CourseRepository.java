package com.ucv.codetech.repository;

import com.ucv.codetech.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

    void deleteById(Long id);
}
