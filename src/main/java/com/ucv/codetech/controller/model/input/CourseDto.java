package com.ucv.codetech.controller.model.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
public class CourseDto {

    @NotBlank(message = "The course name should not be empty")
    private String name;

    @NotBlank(message = "The description should not be empty")
    private String description;

    @NotNull(message = "The category should be specified")
    private Long categoryId;

    @NotNull(message = "Difficulty should not be null")
    private DifficultyDto difficulty;
}
