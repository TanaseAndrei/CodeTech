package com.ucv.codetech.service;

import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.model.Comment;
import com.ucv.codetech.repository.CommentRepositoryGateway;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepositoryGateway commentRepositoryGateway;

    public Comment getCommentById(Long id) {
        return commentRepositoryGateway.findById(id)
                .orElseThrow(() -> new AppException("The comment with id " + id + " has not been found", HttpStatus.NOT_FOUND));
    }

    public void edit(Long id, String description) {
        Comment comment = getCommentById(id);
        comment.setDescription(description);
        commentRepositoryGateway.saveOrUpdate(comment);
    }

    public void deleteComment(Long id) {
        if(!commentRepositoryGateway.existsById(id)) {
            throw new AppException("The comment with the id " + id + " does not exist", HttpStatus.NOT_FOUND);
        }
        commentRepositoryGateway.deleteComment(id);
    }

    public void upVote(Long id) {
        Comment comment = getCommentById(id);
        comment.upVote();
        commentRepositoryGateway.saveOrUpdate(comment);
    }

    public void downVote(Long id) {
        Comment comment = getCommentById(id);
        comment.downVote();
        commentRepositoryGateway.saveOrUpdate(comment);
    }
}
