package com.ucv.codetech.facade;

import com.ucv.codetech.StartupComponent.Facade;
import com.ucv.codetech.controller.model.input.UpdateCommentDto;
import com.ucv.codetech.model.Comment;
import com.ucv.codetech.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Facade
@AllArgsConstructor
public class CommentFacade {

    private final CommentService commentService;

    //TODO return dto
    public Comment getCommentById(Long id) {
        return commentService.findById(id);
    }

    @Transactional
    public void update(Long id, UpdateCommentDto updateComment) {
        commentService.edit(id, updateComment.getDescription());
    }

    @Transactional
    public void delete(Long id) {
        commentService.deleteById(id);
    }

    public void upVote(Long id) {
        commentService.upVote(id);
    }

    public void downVote(Long id) {
        commentService.downVote(id);
    }
}
