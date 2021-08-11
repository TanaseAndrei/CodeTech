package com.ucv.codetech.repository;

import com.ucv.codetech.model.Comment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class CommentRepositoryGateway {

    private final CommentRepository commentRepository;

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    public Long saveOrUpdate(Comment comment) {
        return commentRepository.save(comment).getId();
    }

    public boolean existsById(Long id) {
        return commentRepository.existsById(id);
    }
}
