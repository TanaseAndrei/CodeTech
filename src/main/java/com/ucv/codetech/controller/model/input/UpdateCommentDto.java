package com.ucv.codetech.controller.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
public class UpdateCommentDto {

    @NotEmpty(message = "You should provide an updated comment")
    private String description;
}
