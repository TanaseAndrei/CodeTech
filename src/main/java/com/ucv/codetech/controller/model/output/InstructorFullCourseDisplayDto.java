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
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class InstructorFullCourseDisplayDto extends RepresentationModel<InstructorFullCourseDisplayDto> {

    @ApiModelProperty(value = "The id of the course", example = "1")
    private Long courseId;

    @ApiModelProperty(value = "The name of the course", example = "Java 101")
    private String name;

    @ApiModelProperty(value = "The description of the course")
    private String description;

    @ApiModelProperty(value = "The number of the enrolled students", example = "500")
    private int enrolledStudents;

    @ApiModelProperty(value = "The number of lectures", example = "12")
    private int numberOfLectures;

    @ApiModelProperty(value = "The comments of the course")
    private List<DisplayCommentDto> comments;

    @ApiModelProperty(value = "The image cover name", example = "123v4-123b5432-reyb45y-123b4.jpg")
    private String coverImageName;

    @ApiModelProperty(value = "The lectures of the course")
    private List<InstructorFullLectureDisplayDto> fullLectureDisplayDtos = new ArrayList<>();
}
