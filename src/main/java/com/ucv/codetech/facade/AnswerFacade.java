package com.ucv.codetech.facade;

import com.ucv.codetech.StartupComponent.Facade;
import com.ucv.codetech.controller.model.input.UpdateAnswerDto;
import com.ucv.codetech.model.Answer;
import com.ucv.codetech.service.AnswerService;
import lombok.AllArgsConstructor;

@Facade
@AllArgsConstructor
public class AnswerFacade {

    private final AnswerService answerService;

    public void update(Long id, UpdateAnswerDto updateAnswerDto) {
        Answer answer = answerService.findById(id);
        answer.setCorrectAnswer(updateAnswerDto.isCorrectAnswer());
        answer.setDescription(updateAnswerDto.getDescription());
        answerService.saveOrUpdate(answer);
    }

    public void delete(Long id) {
        answerService.delete(id);
    }

    public boolean isCorrect(Long id) {
        return answerService.isCorrect(id);
    }
}
