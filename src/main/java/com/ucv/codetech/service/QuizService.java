package com.ucv.codetech.service;

import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.model.Quiz;
import com.ucv.codetech.repository.QuizRepositoryGateway;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class QuizService {

    private final QuizRepositoryGateway quizRepositoryGateway;

    public void saveOrUpdate(Quiz quiz) {
        quizRepositoryGateway.saveOrUpdate(quiz);
    }

    public Quiz findById(Long id) {
        return quizRepositoryGateway.findById(id)
                .orElseThrow(() -> new AppException("The quiz with id " + id + " does not exist", HttpStatus.NOT_FOUND));
    }

    public void deleteQuiz(Long id) {
        if(!quizRepositoryGateway.existsQuiz(id)) {
            throw new AppException("The quiz with id " + id + " does not exist", HttpStatus.NOT_FOUND);
        }
        quizRepositoryGateway.deleteById(id);
    }

    public List<Quiz> findAllByInstructorName(String name) {
        return quizRepositoryGateway.findAllByInstructorName(name);
    }
}
