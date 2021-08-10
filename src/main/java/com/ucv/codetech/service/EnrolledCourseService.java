package com.ucv.codetech.service;

import com.ucv.codetech.model.EnrolledCourse;
import com.ucv.codetech.repository.EnrolledCourseRepositoryGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EnrolledCourseService {

    private final EnrolledCourseRepositoryGateway enrolledCourseRepositoryGateway;

    public void enroll(EnrolledCourse enrolledCourse) {
        enrolledCourseRepositoryGateway.createOrUpdate(enrolledCourse);
    }
}
