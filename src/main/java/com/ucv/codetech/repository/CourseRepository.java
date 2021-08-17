package com.ucv.codetech.repository;

import com.ucv.codetech.model.Course;
import com.ucv.codetech.model.projection.CourseCoverImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    void deleteById(Long id);

    @Query("SELECT course FROM Course course WHERE course.available = true")
    Page<Course> findAll(Pageable pageable);

    @Query("SELECT c.folderName FROM Course c WHERE c.id = :id")
    Optional<String> getCourseFolderName(@Param("id") Long id);

    boolean existsByName(String name);

    Optional<Course> findByIdAndInstructorUsername(Long id, String username);

    @Query("SELECT course.coverImageName FROM Course course WHERE course.id = :courseId")
    Optional<CourseCoverImage> getCourseCoverById(@Param("courseId") Long id);
}
