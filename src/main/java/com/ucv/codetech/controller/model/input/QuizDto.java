package com.ucv.codetech.controller.model.input;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class QuizDto {

    private List<QuestionDto> questions;
}
