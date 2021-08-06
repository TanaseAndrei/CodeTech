package com.ucv.codetech.controller;

import com.ucv.codetech.controller.model.input.UpdateCommentDto;
import com.ucv.codetech.controller.swagger.CommentApi;
import com.ucv.codetech.facade.CommentFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
public class CommentController implements CommentApi {

    private final CommentFacade commentFacade;

    @PatchMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void edit(@PathVariable("id") Long id, @RequestBody UpdateCommentDto updateComment) {
        commentFacade.editComment(id, updateComment);
    }

    @PatchMapping(path = "/{id}/upvote")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void upVote(@PathVariable("id") Long id) {
        commentFacade.upVote(id);
    }

    @PatchMapping(path = "/{id}/downvote")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void downVote(@PathVariable("id") Long id) {
        commentFacade.downVote(id);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        commentFacade.deleteComment(id);
    }
}
