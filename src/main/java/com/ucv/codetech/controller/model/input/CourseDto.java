package com.ucv.codetech.controller.model.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
public class CourseDto {

    private String name;

    private String instructorName;

    private String description;

    private Long categoryId;

    @NotNull(message = "Difficulty should not be null")
    private DifficultyDto difficulty;
}
