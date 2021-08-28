package com.ucv.codetech.controller.model.output;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "The course dto used to display a full course object for a student")
public class PreviewFullCourseDto extends RepresentationModel<PreviewFullCourseDto> {

    @ApiModelProperty(value = "The name of the course", example = "Java 101")
    private String name;

    @ApiModelProperty(value = "The name of the instructor", example = "Andrei T.")
    private String instructorName;

    @ApiModelProperty(value = "The description of the course", example = "It is a nice course that will teach you Java from A to Z")
    private String description;

    @ApiModelProperty(value = "The number of the enrolled student", example = "1500")
    private int enrolledStudents;

    @ApiModelProperty(value = "The number of lectures from the course", example = "15")
    private int numberOfLectures;

    @ApiModelProperty(value = "List of comments")
    private List<DisplayCommentDto> comments;

    @ApiModelProperty(value = "The name of the cover image")
    private String coverImageName;

    @ApiModelProperty(value = "The list of lectures")
    private List<DisplayLectureDto> displayLectureDtos = new ArrayList<>();
}
