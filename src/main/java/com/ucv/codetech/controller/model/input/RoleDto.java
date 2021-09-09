package com.ucv.codetech.controller.model.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Roles of a user")
public enum RoleDto {
    @ApiModelProperty(value = "The student user")
    STUDENT,
    @ApiModelProperty(value = "The instructor user")
    INSTRUCTOR
}
