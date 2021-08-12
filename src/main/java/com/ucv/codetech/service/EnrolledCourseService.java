package com.ucv.codetech.service;

import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.model.EnrolledCourse;
import com.ucv.codetech.repository.EnrolledCourseRepositoryGateway;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EnrolledCourseService {

    private final EnrolledCourseRepositoryGateway enrolledCourseRepositoryGateway;

    public void saveOrUpdate(EnrolledCourse enrolledCourse) {
        enrolledCourseRepositoryGateway.saveOrUpdate(enrolledCourse);
    }

    public EnrolledCourse findById(Long id, String username) {
        return enrolledCourseRepositoryGateway.findByIdAndUsername(id, username)
                .orElseThrow(() -> new AppException("The enrolled course with id " + id
                        + " does not exist in user's enrolled courses of student " + username, HttpStatus.NOT_FOUND));
    }
}
