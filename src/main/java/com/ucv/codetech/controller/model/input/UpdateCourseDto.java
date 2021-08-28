package com.ucv.codetech.controller.model.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@ApiModel(description = "The course dto used to update a course")
public class UpdateCourseDto {

    @NotEmpty(message = "You should provide the name of the course")
    @ApiModelProperty(value = "The new name of the course", example = "Java 102")
    private String name;

    @NotNull(message = "You should provide the availability of the course")
    @ApiModelProperty(value = "The new status of the course", example = "false")
    private boolean available;

    @NotEmpty(message = "You should provide a description of the course")
    @ApiModelProperty(value = "The new description of the course")
    private String description;

    @NotNull(message = "You should provide the difficulty of the course")
    @ApiModelProperty(value = "The new difficulty of the course")
    private DifficultyDto difficultyDto;

    @NotNull(message = "You should provide the category of the course")
    @ApiModelProperty(value = "The new category of the course", example = "2")
    private Long categoryId;
}
