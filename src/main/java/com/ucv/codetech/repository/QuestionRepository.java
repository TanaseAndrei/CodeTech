package com.ucv.codetech.repository;

import com.ucv.codetech.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

interface QuestionRepository extends JpaRepository<Question, Long> {

    boolean existsById(Long id);
}
