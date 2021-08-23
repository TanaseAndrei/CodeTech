package com.ucv.codetech.controller.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
public class QuestionDto {

    @NotBlank(message = "The question should not be empty")
    private String question;

    @NotNull(message = "You should specify if there are multiple answers or not")
    private boolean multipleAnswers;

    @NotEmpty(message = "You should provide some answers to this question")
    private List<AnswerDto> answers;
}
