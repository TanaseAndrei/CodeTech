package com.ucv.codetech.repository;

import com.ucv.codetech.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseLectureRepository extends JpaRepository<Lecture, Long> {

    Optional<Lecture> findByIdAndCourseId(Long courseLectureId, Long courseId);

    @Query("SELECT courseLecture.lectureVideoName FROM Lecture courseLecture WHERE courseLecture.course.id = :courseId")
    List<String> getCourseLectureVideos(@Param("courseId")Long courseId);

    List<Lecture> getCourseLecturesByCourseId(Long courseId);
}
