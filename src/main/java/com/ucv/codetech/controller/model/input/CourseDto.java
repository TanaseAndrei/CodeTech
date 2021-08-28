package com.ucv.codetech.controller.model.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@ApiModel(description = "The course dto used to create a course")
public class CourseDto {

    @NotBlank(message = "The course name should not be empty")
    @ApiModelProperty(required = true, value = "The name of the course", example = "Java 101")
    private String name;

    @NotBlank(message = "The description should not be empty")
    @ApiModelProperty(required = true, value = "The description of the course. This contains a summary about this course.",
            example = "It's a nice course that will teach you Java from A to Z.")
    private String description;

    @NotNull(message = "The category should be specified")
    @ApiModelProperty(required = true, value = "The id of the category of the course", example = "1")
    private Long categoryId;

    @NotNull(message = "Difficulty should not be null")
    @ApiModelProperty(required = true, value = "The difficulty of the course", example = "BEGINNER")
    private DifficultyDto difficulty;
}
