package com.ucv.codetech.controller.model.input;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateCourseDto {

    private String name;

    private boolean available;

    private String description;

    private DifficultyDto difficultyDto;

    private Long categoryId;
}
