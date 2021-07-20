package com.ucv.codetech.repository;

import com.ucv.codetech.model.CourseLecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseLectureRepository extends JpaRepository<CourseLecture, Long> {

    Optional<CourseLecture> findByIdAndCourseId(Long courseLectureId, Long courseId);

    @Query("SELECT courseLecture.lectureVideoPath FROM CourseLecture courseLecture WHERE courseLecture.course.id = :courseId")
    List<String> getCourseLectureVideos(@Param("courseId")Long courseId);

    List<CourseLecture> getCourseLecturesByCourseId(Long courseId);
}
