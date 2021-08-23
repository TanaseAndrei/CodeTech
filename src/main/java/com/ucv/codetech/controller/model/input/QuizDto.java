package com.ucv.codetech.controller.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Setter
@Getter
public class QuizDto {

    @NotEmpty(message = "You should provide some question to this quiz")
    private List<QuestionDto> questions;
}
