package com.ucv.codetech.controller.model.output;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "The lecture object used to display a lecture")
public class DisplayLectureDto extends RepresentationModel<DisplayLectureDto> {

    @ApiModelProperty(value = "The id of the lecture", example = "12")
    private Long id;

    @ApiModelProperty(value = "The name of the lecture", example = "Polymorphism")
    private String name;
}
