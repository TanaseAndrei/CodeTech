package com.ucv.codetech.controller.model.output;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;


@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "The course object used to display a student's preview of a course")
public class PreviewCourseDto extends RepresentationModel<PreviewCourseDto> {

    @ApiModelProperty(value = "The id of the course", example = "1")
    private Long courseId;

    @JsonIgnore
    private Long enrolledCourseId;

    @ApiModelProperty(value = "The name of the course", example = "Java 101")
    private String name;

    @ApiModelProperty(value = "The number of enrolled student", example = "1500")
    private int enrolledStudents;

    @ApiModelProperty(value = "The number of comments", example = "175")
    private int numberOfComments;

    @ApiModelProperty(value = "The difficulty of the course", example = "BEGINNER")
    private String difficulty;

    @ApiModelProperty(value = "Field representing if the current student is already enrolled or not in the course", example = "true")
    private boolean isAlreadyEnrolled = false;

    @ApiModelProperty(value = "The name of the cover image of the course", example = "12v321b-12v3123v-12v31.jpg")
    private String coverImageName;
}
