package com.ucv.codetech.service;

import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.model.Question;
import com.ucv.codetech.repository.QuestionRepositoryGateway;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class QuestionService {

    private final QuestionRepositoryGateway questionRepositoryGateway;

    public void saveOrUpdate(Question question) {
        questionRepositoryGateway.saveOrUpdate(question);
    }

    public Question findById(Long id) {
        return questionRepositoryGateway.findById(id)
                .orElseThrow(() -> new AppException("The question with id " + id + " does not exist", HttpStatus.NOT_FOUND));
    }

    public void delete(Long id) {
        if(!questionRepositoryGateway.existsById(id)) {
            throw new AppException("The question with id " + id + " does not exist", HttpStatus.NOT_FOUND);
        }
        questionRepositoryGateway.deleteById(id);
    }
}
