package com.ucv.codetech.controller.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class UpdateCourseDto {

    @NotEmpty(message = "You should provide the name of the course")
    private String name;

    @NotNull(message = "You should provide the availability of the course")
    private boolean available;

    @NotEmpty(message = "You should provide a description of the course")
    private String description;

    @NotNull(message = "You should provide the difficulty of the course")
    private DifficultyDto difficultyDto;

    @NotNull(message = "You should provide the category of the course")
    private Long categoryId;
}
