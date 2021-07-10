package com.ucv.codetech.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class FileService {

    private static final String SLASH = File.separator;

    public String moveCoverFile(MultipartFile multipartFile, String courseName) throws IOException {
        byte[] fileBytes = multipartFile.getBytes();
        String newCoverName = courseName + "-cover-" + System.currentTimeMillis() + multipartFile.getOriginalFilename();
        String coverPath = "E:\\Programming\\CodeTechOutput\\course";
        String savedFilePath = coverPath + SLASH + newCoverName;
        Files.write(Paths.get(savedFilePath), fileBytes);
        return newCoverName;
    }
}
