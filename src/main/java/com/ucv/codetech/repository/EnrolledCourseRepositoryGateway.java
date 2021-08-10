package com.ucv.codetech.repository;

import com.ucv.codetech.model.EnrolledCourse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EnrolledCourseRepositoryGateway {

    private final EnrolledCourseRepository enrolledCourseRepository;

    public void createOrUpdate(EnrolledCourse enrolledCourse) {
        enrolledCourseRepository.save(enrolledCourse);
    }
}
