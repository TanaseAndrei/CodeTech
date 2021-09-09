package com.ucv.codetech.controller.model.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Difficulties of a course")
public enum DifficultyDto {

    @ApiModelProperty(value = "The BEGINNER difficulty of a course")
    BEGINNER,
    @ApiModelProperty(value = "The INTERMEDIATE difficulty of a course")
    INTERMEDIATE,
    @ApiModelProperty(value = "The ADVANCED difficulty of a course")
    ADVANCED;
}
