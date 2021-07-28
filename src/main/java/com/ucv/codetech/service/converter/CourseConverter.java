package com.ucv.codetech.service.converter;

import com.ucv.codetech.controller.model.input.CourseDto;
import com.ucv.codetech.controller.model.output.DisplayCourseDto;
import com.ucv.codetech.controller.model.output.FullDisplayCourseDto;
import com.ucv.codetech.model.Course;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CourseConverter {

    private final CourseLectureConverter courseLectureConverter;

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

    public List<DisplayCourseDto> courseListToDisplayCourseDtoList(List<Course> courses) {
        return courses
                .stream()
                .map(this::courseToDisplayCourseDto)
                .collect(Collectors.toList());
    }

    public FullDisplayCourseDto courseToFullDisplayCourseDto(Course course) {
        FullDisplayCourseDto fullDisplayCourseDto = new FullDisplayCourseDto();
        fullDisplayCourseDto.setNumberOfLectures(course.getLectures().size());
        fullDisplayCourseDto.setCoverImageName(course.getCoverImageName());
        fullDisplayCourseDto.setEnrolledStudents(course.getEnrolledStudents());
        fullDisplayCourseDto.setInstructorName(course.getInstructorName());
        fullDisplayCourseDto.setName(course.getName());
        fullDisplayCourseDto.setDisplayLectureDtos(course.getLectures().stream().map(courseLectureConverter::lectureToDisplayLectureDto).collect(Collectors.toList()));
        return fullDisplayCourseDto;
    }
}
