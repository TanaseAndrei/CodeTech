package com.ucv.codetech.controller.model.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@ApiModel(description = "The student dto used to register a student", parent = AppUserDto.class)
public class StudentDto extends AppUserDto {

    @NotNull(message = "The role should not be null")
    @ApiModelProperty(required = true, value = "The role of the user", example = "STUDENT")
    private RoleDto roleDto;
}
