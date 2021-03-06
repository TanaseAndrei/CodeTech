package com.ucv.codetech.controller;

import com.ucv.codetech.controller.model.input.UpdateCommentDto;
import com.ucv.codetech.controller.swagger.CommentApi;
import com.ucv.codetech.facade.CommentFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
public class CommentController implements CommentApi {

    private final CommentFacade commentFacade;

    @PreAuthorize("hasRole('STUDENT')")
    @PatchMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void edit(@PathVariable("id") Long id, @Valid @RequestBody UpdateCommentDto updateComment) {
        commentFacade.update(id, updateComment);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PatchMapping(path = "/{id}/upvote")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void upVote(@PathVariable("id") Long id) {
        commentFacade.upVote(id);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PatchMapping(path = "/{id}/downvote")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void downVote(@PathVariable("id") Long id) {
        commentFacade.downVote(id);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        commentFacade.delete(id);
    }
}
