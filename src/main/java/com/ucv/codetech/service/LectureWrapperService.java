package com.ucv.codetech.service;

import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.model.LectureWrapper;
import com.ucv.codetech.repository.LectureWrapperRepositoryGateway;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LectureWrapperService {

    private final LectureWrapperRepositoryGateway lectureWrapperRepositoryGateway;

    public void saveOrUpdate(LectureWrapper lectureWrapper) {
        lectureWrapperRepositoryGateway.saveOrUpdate(lectureWrapper);
    }

    public LectureWrapper findById(Long id) {
        return lectureWrapperRepositoryGateway.findById(id)
                .orElseThrow(() -> new AppException(String.format("The lecture wrapper with id %d does not exist", id), HttpStatus.NOT_FOUND));
    }
}
