package com.ucv.codetech.controller.model.output;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "The quiz preview of an instructor")
public class InstructorPreviewQuizDto extends RepresentationModel<InstructorPreviewQuizDto> {

    @ApiModelProperty(value = "The id of thq quizz", example = "1")
    private Long quizId;

    @ApiModelProperty(value = "The name of the quizz", example = "Java 101")
    private String quizName;
}
