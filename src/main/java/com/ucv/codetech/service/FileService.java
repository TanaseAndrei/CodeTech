package com.ucv.codetech.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class FileService {

    private static final String SLASH = File.separator;

    @Value("${course.cover.path}")
    private String courseCoverPath;

    public String moveCoverFile(MultipartFile multipartFile, String courseName) throws IOException {
        byte[] fileBytes = multipartFile.getBytes();
        String newCoverName = courseName + "-cover-" + System.currentTimeMillis() + multipartFile.getOriginalFilename();
        String savedFilePath = courseCoverPath + SLASH + newCoverName;
        Files.write(Paths.get(savedFilePath), fileBytes);
        return newCoverName;
    }

    public String moveVideoLecture(MultipartFile video, String courseLectureName) throws IOException {
        byte[] fileBytes = video.getBytes();
        String newVideoName = courseLectureName + "-video-" + System.currentTimeMillis() + video.getOriginalFilename();
        String savedFilePath = courseCoverPath + SLASH + newVideoName;
        Files.write(Paths.get(savedFilePath), fileBytes);
        return newVideoName;
    }

    public void deleteCover(String path) throws IOException {
        Files.delete(Paths.get(courseCoverPath + SLASH + path));
    }
}
