package com.ucv.codetech.controller.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Setter
@Getter
public class QuestionDto {

    @NotBlank(message = "The question should not be empty")
    private String question;

    private boolean multipleAnswers;

    private List<AnswerDto> answers;
}
