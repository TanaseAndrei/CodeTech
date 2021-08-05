package com.ucv.codetech.controller;

import com.ucv.codetech.controller.model.input.UpdateAnswerDto;
import com.ucv.codetech.facade.AnswerFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answers")
@AllArgsConstructor
public class AnswerController {

    private final AnswerFacade answerFacade;

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") Long id, @RequestBody UpdateAnswerDto updateAnswerDto) {
        answerFacade.update(id, updateAnswerDto);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        answerFacade.delete(id);
    }

    @GetMapping(path = "/{id}/is-correct", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean isCorrect(@PathVariable("id") Long id) {
        return answerFacade.isCorrect(id);
    }
}
