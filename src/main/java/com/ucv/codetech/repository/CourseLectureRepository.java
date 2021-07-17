package com.ucv.codetech.repository;

import com.ucv.codetech.model.CourseLecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseLectureRepository extends JpaRepository<CourseLecture, Long> {

    Optional<CourseLecture> findByIdAndCourseId(Long courseLectureId, Long courseId);
}
