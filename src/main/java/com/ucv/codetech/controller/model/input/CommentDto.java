package com.ucv.codetech.controller.model.input;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentDto {

    private Long studentId;

    private Long courseId;

    private String description;
}
