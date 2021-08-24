package com.ucv.codetech.facade;

import com.ucv.codetech.CodeTechApplication.Facade;
import com.ucv.codetech.controller.model.input.UpdateCommentDto;
import com.ucv.codetech.controller.model.output.DisplayCommentDto;
import com.ucv.codetech.facade.converter.CommentConverter;
import com.ucv.codetech.model.Comment;
import com.ucv.codetech.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Facade
@AllArgsConstructor
@Slf4j
public class CommentFacade {

    private final CommentService commentService;
    private final CommentConverter commentConverter;

    public DisplayCommentDto find(Long id) {
        log.info("Searching comment with id {}", id);
        Comment comment = commentService.findById(id);
        log.info("Found comment with id {}", id);
        return commentConverter.entityToDto(comment);
    }

    @Transactional
    public void update(Long id, UpdateCommentDto updateComment) {
        log.info("Updating the comment with the id {}", id);
        commentService.edit(id, updateComment.getDescription());
        log.info("Updated comment with id {}", id);
    }

    @Transactional
    public void delete(Long id) {
        log.info("Deleting the comment with the id {}", id);
        commentService.deleteById(id);
        log.info("Deleted comment with id {}", id);
    }

    public void upVote(Long id) {
        log.info("Voting the comment with id {}", id);
        commentService.upVote(id);
        log.info("Voted the comment with id {}", id);
    }

    public void downVote(Long id) {
        log.info("Downvoting the comment with the id {}", id);
        commentService.downVote(id);
        log.info("Downvoted the comment with the id {}", id);
    }
}
