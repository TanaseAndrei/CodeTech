package com.ucv.codetech.service;

import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.service.file.FileService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
public class MediaService {

    private final FileService fileService;

    public Resource getMediaAsResource(String folderName, String fileName) {
        try {
            return fileService.getFileAsResource(folderName, fileName);
        } catch (IOException ioException) {
            throw new AppException(ioException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
