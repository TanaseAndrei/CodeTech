package com.ucv.codetech.facade;

import com.ucv.codetech.controller.model.input.UpdateAnswerDto;
import com.ucv.codetech.model.Answer;
import com.ucv.codetech.service.AnswerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import static com.ucv.codetech.CodeTechApplication.Facade;

@Facade
@AllArgsConstructor
@Slf4j
public class AnswerFacade {

    private final AnswerService answerService;

    @Transactional
    public void update(Long id, UpdateAnswerDto updateAnswerDto) {
        Answer answer = answerService.findById(id);
        answer.setCorrectAnswer(updateAnswerDto.isCorrectAnswer());
        answer.setDescription(updateAnswerDto.getDescription());
        answerService.saveOrUpdate(answer);
        log.info("Updated answer with id {}", id);
    }

    @Transactional
    public void delete(Long id) {
        answerService.deleteById(id);
        log.info("Deleted answer with id {}", id);
    }

    public boolean isCorrect(Long id) {
        log.info("Checking answer with id {} if it is correct or not", id);
        return answerService.isCorrect(id);
    }
}
