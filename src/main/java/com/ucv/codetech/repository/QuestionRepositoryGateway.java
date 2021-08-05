package com.ucv.codetech.repository;

import com.ucv.codetech.model.Question;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class QuestionRepositoryGateway {

    private final QuestionRepository questionRepository;

    public Optional<Question> findById(Long id) {
        return questionRepository.findById(id);
    }

    public void saveOrUpdate(Question question) {
        questionRepository.save(question);
    }

    public boolean existsById(Long id) {
        return questionRepository.existsById(id);
    }

    public void deleteById(Long id) {
        questionRepository.deleteById(id);
    }
}
