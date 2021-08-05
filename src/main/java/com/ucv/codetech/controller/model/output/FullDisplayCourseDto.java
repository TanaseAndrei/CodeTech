package com.ucv.codetech.controller.model.output;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
public class FullDisplayCourseDto extends RepresentationModel<FullDisplayCourseDto> {

    private String name;

    private String instructorName;

    private String description;

    private int enrolledStudents;

    private int numberOfLectures;

    private List<DisplayCommentDto> comments;

    @JsonIgnore
    private String coverImageName;

    private List<DisplayLectureDto> displayLectureDtos;
}
