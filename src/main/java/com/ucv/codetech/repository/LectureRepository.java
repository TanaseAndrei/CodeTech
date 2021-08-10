package com.ucv.codetech.repository;

import com.ucv.codetech.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lecture, Long> {

    Optional<Lecture> findByIdAndCourseId(Long lectureId, Long courseId);

    @Query("SELECT lecture.lectureVideoName FROM Lecture lecture WHERE lecture.course.id = :courseId")
    List<String> getLectureVideoNames(@Param("courseId")Long courseId);

    List<Lecture> getLecturesByCourseId(Long courseId);

    @Query("SELECT lecture.course.folderName FROM Lecture lecture WHERE lecture.id = :lectureId")
    Optional<String> getAssociatedCourseFolder(@Param("lectureId") Long id);

    boolean existsByNameAndCourseId(String lectureName, Long id);
}
