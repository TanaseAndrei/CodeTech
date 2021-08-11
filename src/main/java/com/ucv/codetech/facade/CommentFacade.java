package com.ucv.codetech.facade;

import com.ucv.codetech.StartupComponent.Facade;
import com.ucv.codetech.controller.model.input.UpdateCommentDto;
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

    public void update(Long id, UpdateCommentDto updateComment) {
        commentService.edit(id, updateComment.getDescription());
    }

    public void delete(Long id) {
        commentService.deleteComment(id);
    }

    public void upVote(Long id) {
        commentService.upVote(id);
    }

    public void downVote(Long id) {
        commentService.downVote(id);
    }
}
