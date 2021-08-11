package com.ucv.codetech.controller.model.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class AnswerDto {

    @NotBlank(message = "You should specify the possible answer")
    private String answer;

    @JsonProperty
    @NotNull(message = "You should specify if the answer is correct or not")
    private boolean isCorrectAnswer;
}
