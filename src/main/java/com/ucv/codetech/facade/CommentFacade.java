package com.ucv.codetech.facade;

import com.ucv.codetech.StartupComponent.Facade;
import com.ucv.codetech.model.Comment;
import com.ucv.codetech.service.CommentService;
import lombok.AllArgsConstructor;

@Facade
@AllArgsConstructor
public class CommentFacade {

    private final CommentService commentService;

    //TODO return dto
    public Comment getCommentById(Long id) {
        return commentService.getCommentById(id);
    }

    public void editComment(Long id, String description) {
        commentService.edit(id, description);
    }

    public void deleteComment(Long id) {
        commentService.deleteComment(id);
    }

    public void upVote(Long id) {
        commentService.upVote(id);
    }

    public void downVote(Long id) {
        commentService.downVote(id);
    }
}
