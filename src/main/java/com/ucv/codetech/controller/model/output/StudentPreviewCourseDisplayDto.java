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
@ApiModel(description = "This represents the preview of an enrolled course.")
public class StudentPreviewCourseDisplayDto extends RepresentationModel<StudentPreviewCourseDisplayDto> {

    @ApiModelProperty(value = "The id of the course", example = "1")
    private Long enrolledCourseId;

    @ApiModelProperty(value = "The name of the course", example = "Java 101")
    private String name;

    @ApiModelProperty(value = "The number of the completed lectures from the current course", example = "5")
    private int numberOfCompletedLectures;

    @ApiModelProperty(value = "The number of the total lectures from the current course", example = "15")
    private int numberOfLectures;

    @ApiModelProperty(value = "The status of the current enrolled course, if the student completed it or not", example = "true")
    private boolean isCourseCompleted;

    @ApiModelProperty(value = "The name of the cover image associated with the course", example = "213vr321v-2r3vr123v123-v341.jpg")
    private String coverImageName;
}
