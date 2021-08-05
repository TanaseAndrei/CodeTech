package com.ucv.codetech.controller.model.input;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class QuestionDto {

    private String question;

    private boolean multipleAnswers;

    private List<AnswerDto> answers;
}
