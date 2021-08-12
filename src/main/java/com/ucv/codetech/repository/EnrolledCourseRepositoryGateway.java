package com.ucv.codetech.repository;

import com.ucv.codetech.model.EnrolledCourse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class EnrolledCourseRepositoryGateway {

    private final EnrolledCourseRepository enrolledCourseRepository;

    public Optional<EnrolledCourse> findByIdAndUsername(Long id, String username) {
        return enrolledCourseRepository.findByIdAndStudentUsername(id, username);
    }

    public void saveOrUpdate(EnrolledCourse enrolledCourse) {
        enrolledCourseRepository.save(enrolledCourse);
    }
}
