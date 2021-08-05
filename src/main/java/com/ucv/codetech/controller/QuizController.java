package com.ucv.codetech.controller;

import com.ucv.codetech.controller.model.output.DisplayQuizDto;
import com.ucv.codetech.facade.QuizFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quiz")
@AllArgsConstructor
public class QuizController {

    //TODO implement the flow for quizzes: getById, getAll, delete a quizz + for its subentities like question and answer
    //TODO implement endpoint to check if answer is correct
    private final QuizFacade quizFacade;

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DisplayQuizDto getQuiz(@PathVariable("id") Long id) {
        return quizFacade.getQuiz(id);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuiz(@PathVariable("id") Long id) {
        quizFacade.deleteQuiz(id);
    }
}
