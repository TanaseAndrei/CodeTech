package com.ucv.codetech.controller;

import com.ucv.codetech.controller.model.input.AnswerDto;
import com.ucv.codetech.controller.model.input.UpdateQuestionDto;
import com.ucv.codetech.facade.QuestionFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/questions")
@AllArgsConstructor
public class QuestionController {

    private final QuestionFacade questionFacade;

    @PostMapping(path = "/{id}/add-answer", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addAnswer(@PathVariable("id") Long id, @RequestBody AnswerDto answerDto) {
        questionFacade.addAnswer(id, answerDto);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateQuestion(@PathVariable("id") Long id, @RequestBody UpdateQuestionDto updateQuestionDto) {
        questionFacade.update(id, updateQuestionDto);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuestion(@PathVariable("id") Long id) {
        questionFacade.delete(id);
    }
}
