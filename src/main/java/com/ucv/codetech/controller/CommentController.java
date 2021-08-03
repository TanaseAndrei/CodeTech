package com.ucv.codetech.controller;

import com.ucv.codetech.facade.CommentFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentFacade commentFacade;

    //TODO here a dto will be needed to update the description
    @PatchMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void edit(@PathVariable("id") Long id, @RequestBody String description) {
        commentFacade.editComment(id, description);
    }

    //TODO for upvoting and downvoting, we need to save the user too because an user, have to upvote only once for a comment, not twice
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
