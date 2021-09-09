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
@ApiModel(description = "The dto used to display a full lecture of an instructor")
public class InstructorFullLectureDisplayDto extends RepresentationModel<InstructorFullLectureDisplayDto> {

    @ApiModelProperty(value = "The id of the lecture", example = "1")
    private Long lectureId;

    @ApiModelProperty(value = "The description of the lecture")
    private String description;

    @ApiModelProperty(value = "The files associated to the lecture")
    private List<String> lectureFileNames = new ArrayList<>();

    @ApiModelProperty(value = "The video name of the lecture", example = "12341235-12351235-235-23423.mp4")
    private String lectureVideoName;
}
