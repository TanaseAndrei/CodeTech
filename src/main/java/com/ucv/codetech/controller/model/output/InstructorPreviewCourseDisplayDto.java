package com.ucv.codetech.controller.model.output;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "Course preview")
public class InstructorPreviewCourseDisplayDto extends RepresentationModel<InstructorPreviewCourseDisplayDto> {

    @ApiModelProperty(value = "The id of the course", example = "1")
    private Long courseId;

    @ApiModelProperty(value = "The name of the course", example = "Java 101")
    private String name;

    @ApiModelProperty(value = "The number of lectures of the course", example = "25")
    private int numberOfLectures;

    @ApiModelProperty(value = "The number of comments of the course", example = "756")
    private int numberOfComments;

    @ApiModelProperty(value = "The number of the enrolled students", example = "1599s")
    private int numberOfEnrolledStudents;

    @ApiModelProperty(value = "The name of the cover image of the course", example = "123vr123-vr123v32v23-v2342.jpg")
    private String coverImage;
}
