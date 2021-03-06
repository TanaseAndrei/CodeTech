package com.ucv.codetech.repository;

import com.ucv.codetech.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

interface CommentRepository extends JpaRepository<Comment, Long> {

    boolean existsById(Long id);
}
