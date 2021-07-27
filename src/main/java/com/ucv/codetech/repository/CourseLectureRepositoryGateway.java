package com.ucv.codetech.repository;

import com.ucv.codetech.model.Lecture;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class CourseLectureRepositoryGateway {

    private final CourseLectureRepository courseLectureRepository;

    public void save(Lecture lecture) {
        courseLectureRepository.save(lecture);
    }

    public Optional<Lecture> findByCourseLectureIdAndCourseId(Long courseLectureId, Long courseId) {
        return courseLectureRepository.findByIdAndCourseId(courseLectureId, courseId);
    }

    public List<String> getCourseLectureVideos(Long courseId) {
        return courseLectureRepository.getCourseLectureVideos(courseId);
    }

    public List<Lecture> getCourseLecturesByCourseId(Long courseId) {
        return courseLectureRepository.getCourseLecturesByCourseId(courseId);
    }
}
