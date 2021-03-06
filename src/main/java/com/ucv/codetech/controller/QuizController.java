package com.ucv.codetech.controller;

import com.ucv.codetech.controller.model.input.QuestionDto;
import com.ucv.codetech.controller.model.output.DisplayQuizDto;
import com.ucv.codetech.controller.swagger.QuizApi;
import com.ucv.codetech.facade.AuthenticationFacade;
import com.ucv.codetech.facade.QuizFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/quiz")
@AllArgsConstructor
public class QuizController implements QuizApi {

    private final QuizFacade quizFacade;
    private final AuthenticationFacade authenticationFacade;

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PostMapping(path = "/{id}/questions", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Long addQuestion(@PathVariable("id") Long id, @Valid @RequestBody QuestionDto questionDto) {
        return quizFacade.addQuestion(id, questionDto);
    }

    @PreAuthorize("hasAnyRole('STUDENT, INSTRUCTOR')")
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DisplayQuizDto getQuiz(@PathVariable("id") Long id) {
        String currentLoggedUser = authenticationFacade.getAuthentication().getName();
        return quizFacade.getQuiz(id, currentLoggedUser);
    }

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuiz(@PathVariable("id") Long id) {
        quizFacade.deleteQuiz(id);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping(path = "/{id}/complete", produces = MediaType.APPLICATION_JSON_VALUE)
    public Long completeQuiz(@PathVariable("id") Long id) {
        String currentLoggedUser = authenticationFacade.getAuthentication().getName();
        return quizFacade.completeQuiz(id, currentLoggedUser);
    }
}
