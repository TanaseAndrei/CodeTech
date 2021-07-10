package com.ucv.codetech.service.converter;

import com.ucv.codetech.controller.model.CourseDto;
import com.ucv.codetech.model.Course;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CourseConverter {

    public Course courseDtoToCourse(CourseDto courseDto) {
        Course course = new Course();
        course.setName(courseDto.getName());
        course.setInstructorName(courseDto.getInstructorName());
        course.setDescription(courseDto.getDescription());
        course.setComments(Collections.emptyList());
        course.setCourseLectures(Collections.emptyList());
        return course;
    }
}
