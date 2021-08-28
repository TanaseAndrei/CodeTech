package com.ucv.codetech.controller;

import com.ucv.codetech.controller.model.input.AnswerDto;
import com.ucv.codetech.controller.model.input.UpdateQuestionDto;
import com.ucv.codetech.controller.swagger.QuestionApi;
import com.ucv.codetech.facade.QuestionFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/questions")
@AllArgsConstructor
public class QuestionController implements QuestionApi {

    private final QuestionFacade questionFacade;

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PostMapping(path = "/{id}/add-answer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Long addAnswer(@PathVariable("id") Long id, @Valid @RequestBody AnswerDto answerDto) {
        return questionFacade.addAnswer(id, answerDto);
    }

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateQuestion(@PathVariable("id") Long id, @Valid @RequestBody UpdateQuestionDto updateQuestionDto) {
        questionFacade.update(id, updateQuestionDto);
    }

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuestion(@PathVariable("id") Long id) {
        questionFacade.delete(id);
    }
}
