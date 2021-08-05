package com.ucv.codetech.repository;

import com.ucv.codetech.model.Answer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class AnswerRepositoryGateway {

    private final AnswerRepository answerRepository;

    public Optional<Answer> findById(Long id) {
        return answerRepository.findById(id);
    }

    public void saveOrUpdate(Answer answer) {
        answerRepository.save(answer);
    }

    public boolean existsById(Long id) {
        return answerRepository.existsById(id);
    }

    public void deleteById(Long id) {
        answerRepository.deleteById(id);
    }
}
