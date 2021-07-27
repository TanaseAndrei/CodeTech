package com.ucv.codetech.service.converter;

import com.ucv.codetech.controller.model.CourseDto;
import com.ucv.codetech.controller.model.DisplayCourseDto;
import com.ucv.codetech.model.Course;
import com.ucv.codetech.service.file.FileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@AllArgsConstructor
public class CourseConverter {

    private final FileService fileService;

    public Course courseDtoToCourse(CourseDto courseDto) {
        Course course = new Course();
        course.setName(courseDto.getName());
        course.setInstructorName(courseDto.getInstructorName());
        course.setDescription(courseDto.getDescription());
        course.setComments(Collections.emptyList());
        course.setLectures(Collections.emptyList());
        return course;
    }

    public DisplayCourseDto courseToDisplayCourseDto(Course course) {
        DisplayCourseDto displayCourseDto = new DisplayCourseDto();
        displayCourseDto.setCoverImageName(course.getCoverImageName());
        displayCourseDto.setId(course.getId());
        displayCourseDto.setNumberOfLectures(course.getLectures().size());
        displayCourseDto.setName(course.getName());
        displayCourseDto.setInstructorName(course.getInstructorName());
        displayCourseDto.setEnrolledStudents(course.getEnrolledStudents());
        return displayCourseDto;
    }
}
