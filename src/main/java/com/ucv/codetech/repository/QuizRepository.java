package com.ucv.codetech.repository;

import com.ucv.codetech.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    boolean existsById(Long id);
}
