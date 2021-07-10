package com.ucv.codetech.controller.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CourseDto {

    private String name;

    private String instructorName;

    private String description;

    private Long categoryId;
}
