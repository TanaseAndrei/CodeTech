package com.ucv.codetech.repository;

import com.ucv.codetech.model.Quiz;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class QuizRepositoryGateway {

    private final QuizRepository quizRepository;

    public Optional<Quiz> findById(Long id) {
        return quizRepository.findById(id);
    }

    public void deleteById(Long id) {
        quizRepository.deleteById(id);
    }

    public boolean existsQuiz(Long id) {
        return quizRepository.existsById(id);
    }

    public void saveOrUpdate(Quiz quiz) {
        quizRepository.save(quiz);
    }
}
