package com.ucv.codetech.controller.model.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public enum DifficultyDto {

    @ApiModelProperty(value = "The beginner difficulty of a course")
    BEGINNER,
    @ApiModelProperty(value = "The intermediate difficulty of a course")
    INTERMEDIATE,
    @ApiModelProperty(value = "The ADVANCED difficulty of a course")
    ADVANCED;
}
