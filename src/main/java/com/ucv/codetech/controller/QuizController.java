package com.ucv.codetech.controller;

import com.ucv.codetech.controller.model.input.QuestionDto;
import com.ucv.codetech.controller.model.output.DisplayQuizDto;
import com.ucv.codetech.controller.swagger.QuizApi;
import com.ucv.codetech.facade.QuizFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quiz")
@AllArgsConstructor
public class QuizController implements QuizApi {

    private final QuizFacade quizFacade;

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PostMapping(path = "/{id}/add-question", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addQuestion(@PathVariable("id") Long id, @RequestBody QuestionDto questionDto) {
        quizFacade.addQuestion(id, questionDto);
    }

    @PreAuthorize("hasRole('STUDENT, INSTRUCTOR')")
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DisplayQuizDto getQuiz(@PathVariable("id") Long id) {
        return quizFacade.getQuiz(id);
    }

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuiz(@PathVariable("id") Long id) {
        quizFacade.deleteQuiz(id);
    }
}
