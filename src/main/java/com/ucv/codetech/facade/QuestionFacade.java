package com.ucv.codetech.facade;

import com.ucv.codetech.StartupComponent.Facade;
import com.ucv.codetech.controller.model.input.UpdateQuestionDto;
import com.ucv.codetech.model.Question;
import com.ucv.codetech.service.QuestionService;
import lombok.AllArgsConstructor;

@Facade
@AllArgsConstructor
public class QuestionFacade {

    private final QuestionService questionService;

    public void update(Long id, UpdateQuestionDto updateQuestionDto) {
        Question question = questionService.findById(id);
        question.setQuestionContent(updateQuestionDto.getQuestion());
        question.setMultipleAnswers(updateQuestionDto.isMultipleAnswers());
        questionService.saveOrUpdate(question);
    }

    public void delete(Long id) {
        questionService.delete(id);
    }
}
