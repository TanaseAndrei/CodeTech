package com.ucv.codetech.controller.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
public class CommentDto {

    @NotEmpty(message = "The comment should not be empty")
    private String description;
}
