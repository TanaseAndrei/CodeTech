package com.ucv.codetech.controller.model.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Setter
@Getter
@ApiModel(description = "The quiz dto used to create a quiz")
public class QuizDto {

    @NotEmpty(message = "You should provide some question to this quiz")
    @ApiModelProperty(required = true, value = "Question of the quiz that will be created")
    private List<QuestionDto> questions;
}
