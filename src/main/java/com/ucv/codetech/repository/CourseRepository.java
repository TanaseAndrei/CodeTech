package com.ucv.codetech.repository;

import com.ucv.codetech.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    void deleteById(Long id);

    @Query("SELECT c.folderName FROM Course c WHERE c.id = :id")
    Optional<String> getCourseFolderName(@Param("id") Long id);

    @Query("SELECT c.folderName FROM Course c WHERE c.name = :courseName")
    Optional<String> getCourseFolderName(@Param("courseName") String courseName);

    boolean existsByName(String name);
}
