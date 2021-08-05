package com.ucv.codetech.controller.model.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateQuestionDto {

    private String question;

    @JsonProperty
    private boolean multipleAnswers;
}
