package com.ucv.codetech.controller.model.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@ApiModel(description = "The student dto used to register a student", parent = AppUserDto.class)
public class InstructorDto extends AppUserDto {

    @NotNull(message = "The role should not be null")
    @ApiModelProperty(required = true)
    @Schema(description = "The role of the instructor", example = "INSTRUCTOR")
    private RoleDto roleDto;
}
