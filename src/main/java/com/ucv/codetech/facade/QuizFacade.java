package com.ucv.codetech.facade;

import com.ucv.codetech.StartupComponent.Facade;
import com.ucv.codetech.controller.model.output.DisplayQuizDto;
import com.ucv.codetech.facade.converter.QuizConverter;
import com.ucv.codetech.model.Quiz;
import com.ucv.codetech.service.QuizService;
import lombok.AllArgsConstructor;

@Facade
@AllArgsConstructor
public class QuizFacade {

    private final QuizService quizService;
    private final QuizConverter quizConverter;

    public DisplayQuizDto getQuiz(Long id) {
        Quiz quiz = quizService.findById(id);
        return quizConverter.entityToDisplayQuizDto(quiz);
    }

    public void deleteQuiz(Long id) {
        quizService.deleteQuiz(id);
    }
}
