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

    public Long saveOrUpdate(Comment comment) {
        return commentRepositoryGateway.saveOrUpdate(comment);
    }

    public Comment findById(Long id) {
        return commentRepositoryGateway.findById(id)
                .orElseThrow(() -> new AppException(String.format("The comment with the id %d does not exist", id), HttpStatus.NOT_FOUND));
    }

    public void edit(Long id, String description) {
        Comment comment = findById(id);
        comment.setDescription(description);
        commentRepositoryGateway.saveOrUpdate(comment);
    }

    public void deleteById(Long id) {
        if(!commentRepositoryGateway.existsById(id)) {
            throw new AppException(String.format("The comment with the id %d does not exist", id), HttpStatus.NOT_FOUND);
        }
        commentRepositoryGateway.deleteComment(id);
    }

    public void upVote(Long id) {
        Comment comment = findById(id);
        comment.upVote();
        commentRepositoryGateway.saveOrUpdate(comment);
    }

    public void downVote(Long id) {
        Comment comment = findById(id);
        comment.downVote();
        commentRepositoryGateway.saveOrUpdate(comment);
    }
}
