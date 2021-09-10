package com.ucv.codetech.repository;

import com.ucv.codetech.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

interface AnswerRepository extends JpaRepository<Answer, Long> {

    boolean existsById(Long id);
}
