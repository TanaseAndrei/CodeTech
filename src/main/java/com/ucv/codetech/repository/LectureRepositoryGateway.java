package com.ucv.codetech.repository;

import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.model.Lecture;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class LectureRepositoryGateway {

    private final LectureRepository lectureRepository;

    public Lecture saveOrUpdate(Lecture lecture) {
        return lectureRepository.save(lecture);
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

    public void deleteById(Long id) {
        lectureRepository.deleteById(id);
    }

    public Optional<String> getAssociatedCourseFolder(Long lectureId) {
        return lectureRepository.getAssociatedCourseFolder(lectureId);
    }

    public Optional<Lecture> getById(Long id) {
        return lectureRepository.findById(id);
    }
}
