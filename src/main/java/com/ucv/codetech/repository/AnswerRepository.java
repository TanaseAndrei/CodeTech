package com.ucv.codetech.repository;

import com.ucv.codetech.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    boolean existsById(Long id);

    @Query("SELECT answer.isCorrectAnswer FROM Answer answer WHERE answer.id = :id")
    boolean isCorrect(@Param("id") Long id);
}
