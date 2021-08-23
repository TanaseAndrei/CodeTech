package com.ucv.codetech.service;

import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.model.Answer;
import com.ucv.codetech.repository.AnswerRepositoryGateway;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AnswerService {

    private final AnswerRepositoryGateway answerRepositoryGateway;

    public void saveOrUpdate(Answer answer) {
        answerRepositoryGateway.saveOrUpdate(answer);
    }

    public Answer findById(Long id) {
        return answerRepositoryGateway.findById(id)
                .orElseThrow(() -> new AppException("The answer with the id " + id + " does not exist", HttpStatus.NOT_FOUND));
    }

    public void deleteById(Long id) {
        if(!answerRepositoryGateway.existsById(id)) {
            throw new AppException("The answer with the id " + id + " does not exist", HttpStatus.NOT_FOUND);
        }
        answerRepositoryGateway.deleteById(id);
    }

    public boolean isCorrect(Long id) {
        Answer answer = findById(id);
        return answer.isCorrectAnswer();
    }
}
