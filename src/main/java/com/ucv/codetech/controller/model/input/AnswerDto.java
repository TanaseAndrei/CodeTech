package com.ucv.codetech.controller.model.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AnswerDto {

    private String answer;

    @JsonProperty
    private boolean isCorrectAnswer;
}
