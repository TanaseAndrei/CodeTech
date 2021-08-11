package com.ucv.codetech.facade.converter;

import com.ucv.codetech.controller.model.input.CourseDto;
import com.ucv.codetech.controller.model.output.DisplayCourseDto;
import com.ucv.codetech.controller.model.output.FullDisplayCourseDto;
import com.ucv.codetech.controller.model.output.StudentCourseDisplayDto;
import com.ucv.codetech.model.Course;
import com.ucv.codetech.model.Difficulty;
import com.ucv.codetech.model.EnrolledCourse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CourseConverter {

    private final CommentConverter commentConverter;
    private final LectureConverter lectureConverter;

    public Course dtoToEntity(CourseDto courseDto) {
        Course course = new Course();
        course.setName(courseDto.getName());
        course.setDescription(courseDto.getDescription());
        course.setComments(Collections.emptyList());
        course.setLectures(Collections.emptyList());
        course.setDifficulty(Difficulty.getByName(courseDto.getDifficulty().name()));
        return course;
    }

    public List<DisplayCourseDto> courseListToDisplayCourseDtoList(List<Course> courses) {
        return courses
                .stream()
                .map(this::entityToDisplayCourseDto)
                .collect(Collectors.toList());
    }

    public DisplayCourseDto entityToDisplayCourseDto(Course course) {
        DisplayCourseDto displayCourseDto = new DisplayCourseDto();
        displayCourseDto.setCoverImageName(course.getCoverImageName());
        displayCourseDto.setId(course.getId());
        displayCourseDto.setNumberOfLectures(course.getNumberOfLectures());
        displayCourseDto.setNumberOfComments(course.getNumberOfComments());
        displayCourseDto.setName(course.getName());
        displayCourseDto.setInstructorName(course.getInstructor().getUsername());
        displayCourseDto.setEnrolledStudents(course.getNrOfEnrolledStudents());
        displayCourseDto.setDifficulty(course.getDifficulty().toString());
        return displayCourseDto;
    }

    public FullDisplayCourseDto entityToFullDisplayCourseDto(Course course) {
        FullDisplayCourseDto fullDisplayCourseDto = new FullDisplayCourseDto();
        fullDisplayCourseDto.setNumberOfLectures(course.getLectures().size());
        fullDisplayCourseDto.setCoverImageName(course.getCoverImageName());
        fullDisplayCourseDto.setEnrolledStudents(course.getNrOfEnrolledStudents());
        fullDisplayCourseDto.setInstructorName(course.getInstructor().getUsername());
        fullDisplayCourseDto.setName(course.getName());
        fullDisplayCourseDto.setDescription(course.getDescription());
        fullDisplayCourseDto.setComments(commentConverter.entitiesToDisplayCommentDtos(course.getComments()));
        fullDisplayCourseDto.setDisplayLectureDtos(course.getLectures().stream().map(lectureConverter::entityToDisplayLectureDto).collect(Collectors.toList()));
        return fullDisplayCourseDto;
    }
}
