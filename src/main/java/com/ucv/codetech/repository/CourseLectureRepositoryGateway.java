package com.ucv.codetech.repository;

import com.ucv.codetech.model.CourseLecture;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class CourseLectureRepositoryGateway {

    private final CourseLectureRepository courseLectureRepository;

    public Optional<CourseLecture> findByCourseLectureIdAndCourseId(Long courseLectureId, Long courseId) {
        return courseLectureRepository.findByIdAndCourseId(courseLectureId, courseId);
    }
}
