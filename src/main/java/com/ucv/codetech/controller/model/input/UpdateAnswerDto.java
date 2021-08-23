package com.ucv.codetech.controller.model.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class UpdateAnswerDto {

    @NotEmpty(message = "You should provide a description to the answer")
    private String description;

    @JsonProperty
    @NotNull(message = "You should specify if it's the correct answer or not")
    private boolean correctAnswer;
}
