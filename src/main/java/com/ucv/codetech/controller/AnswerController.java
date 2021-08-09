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

import java.security.Principal;

@RestController
@RequestMapping("/answers")
@AllArgsConstructor
@Slf4j
public class AnswerController implements AnswerApi {

    private final AnswerFacade answerFacade;

    @PreAuthorize("hasAnyRole('ROLE_INSTRUCTOR')")
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") Long id, @RequestBody UpdateAnswerDto updateAnswerDto, Principal principal) {
        log.info("Instructor {} is trying to access put method", principal.getName());
        answerFacade.update(id, updateAnswerDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_INSTRUCTOR')")
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        answerFacade.delete(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_STUDENT')")
    @GetMapping(path = "/{id}/is-correct", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean isCorrect(@PathVariable("id") Long id, Principal principal) {
        log.info("Student {} is trying to access is-correct", principal.getName());
        return answerFacade.isCorrect(id);
    }
}
