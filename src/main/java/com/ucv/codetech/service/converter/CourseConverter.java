package com.ucv.codetech.service.converter;

import com.google.common.primitives.Bytes;
import com.ucv.codetech.controller.model.CourseDto;
import com.ucv.codetech.controller.model.DisplayCourseDto;
import com.ucv.codetech.model.Course;
import com.ucv.codetech.service.file.FileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        course.setCourseLectures(Collections.emptyList());
        return course;
    }

    public DisplayCourseDto courseToDisplayCourseDto(Course course) {
        DisplayCourseDto displayCourseDto = new DisplayCourseDto();
        displayCourseDto.setId(course.getId());
        displayCourseDto.setCategory(course.getCategory().getName());
        displayCourseDto.setName(course.getName());
        displayCourseDto.setInstructorName(course.getInstructorName());
        displayCourseDto.setEnrolledStudents(course.getEnrolledStudents());
        displayCourseDto.setNumberOfLectures(course.getCourseLectures().size());
        displayCourseDto.setCoverImage(getBytesOfCoverImage(course.getName(), course.getCoverImagePath()));
        return displayCourseDto;
    }

    private byte[] getBytesOfCoverImage(String courseName, String coverImagePath) {
        try {
            Path coverPath = fileService.getCoverPath(courseName, coverImagePath);
            return Files.readAllBytes(coverPath);
        } catch (IOException ioException) {
            return new byte[0];
        }
    }
}
