package com.ucv.codetech.repository;

import com.ucv.codetech.model.Lecture;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class LectureRepositoryGateway {

    private final LectureRepository lectureRepository;

    public void saveOrUpdate(Lecture lecture) {
        lectureRepository.save(lecture);
    }

    public Optional<Lecture> findByLectureIdAndCourseId(Long lectureId, Long courseId) {
        return lectureRepository.findByIdAndCourseId(lectureId, courseId);
    }

    public List<String> getLectureVideos(Long courseId) {
        return lectureRepository.getLectureVideoNames(courseId);
    }

    public List<Lecture> getLecturesByCourseId(Long courseId) {
        return lectureRepository.getLecturesByCourseId(courseId);
    }
}
