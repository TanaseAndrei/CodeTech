package com.ucv.codetech.controller.model.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class UpdateQuestionDto {

    @NotEmpty(message = "You should provide the question")
    private String question;

    @JsonProperty
    @NotNull(message = "You should specify if the question has multiple answers or not")
    private boolean multipleAnswers;
}
