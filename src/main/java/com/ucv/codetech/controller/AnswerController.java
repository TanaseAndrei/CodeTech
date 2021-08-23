package com.ucv.codetech.controller;

import com.ucv.codetech.controller.model.input.UpdateAnswerDto;
import com.ucv.codetech.controller.swagger.AnswerApi;
import com.ucv.codetech.facade.AnswerFacade;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/answers")
@AllArgsConstructor
@Slf4j
public class AnswerController implements AnswerApi {

    private final AnswerFacade answerFacade;

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") Long id, @Valid @RequestBody UpdateAnswerDto updateAnswerDto) {
        answerFacade.update(id, updateAnswerDto);
    }

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        answerFacade.delete(id);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping(path = "/{id}/is-correct", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean isCorrect(@PathVariable("id") Long id) {
        return answerFacade.isCorrect(id);
    }
}
