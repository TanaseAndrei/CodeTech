package com.ucv.codetech.facade;

import com.ucv.codetech.StartupComponent.Facade;
import com.ucv.codetech.controller.model.input.AnswerDto;
import com.ucv.codetech.controller.model.input.UpdateQuestionDto;
import com.ucv.codetech.facade.converter.QuizConverter;
import com.ucv.codetech.model.Answer;
import com.ucv.codetech.model.Question;
import com.ucv.codetech.service.QuestionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Facade
@AllArgsConstructor
@Slf4j
public class QuestionFacade {

    private final QuizConverter quizConverter;
    private final QuestionService questionService;

    @Transactional
    public Long addAnswer(Long id, AnswerDto answerDto) {
        Question question = questionService.findById(id);
        Answer answer = quizConverter.answerDtoToEntity(answerDto);
        question.addAnswer(answer);
        questionService.saveOrUpdate(question);
        log.info("Added new answer to question {}", id);
        return answer.getId();
    }

    @Transactional
    public void update(Long id, UpdateQuestionDto updateQuestionDto) {
        Question question = questionService.findById(id);
        question.setQuestionContent(updateQuestionDto.getQuestion());
        question.setMultipleAnswers(updateQuestionDto.isMultipleAnswers());
        questionService.saveOrUpdate(question);
        log.info("Updated the question {}", id);
    }

    @Transactional
    public void delete(Long id) {
        questionService.delete(id);
        log.info("Deleted the question {}", id);
    }
}
