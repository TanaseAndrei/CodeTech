package com.ucv.codetech.service.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    private static final String SLASH = File.separator;

    @Value("${application.base-folder}")
    private String applicationBaseFolder;

    public String createCourseFolder(String courseName) throws IOException {
        if(!Files.exists(Paths.get(applicationBaseFolder + courseName))) {
            Files.createDirectory(Paths.get(applicationBaseFolder + SLASH + courseName));
        }
        return courseName;
    }

    public Path getCoverPath(String courseName, String coverName) {
        return Paths.get(applicationBaseFolder + SLASH + courseName + SLASH + coverName);
    }

    public String moveCoverFile(MultipartFile multipartFile, String courseName, String folder) throws IOException {
        byte[] fileBytes = multipartFile.getBytes();
        String newCoverName = courseName + "-cover-" + System.currentTimeMillis() + multipartFile.getOriginalFilename();
        String savedFilePath = applicationBaseFolder + SLASH + folder + SLASH + newCoverName;
        Files.write(Paths.get(savedFilePath), fileBytes);
        return newCoverName;
    }

    public String moveVideoLecture(MultipartFile video, String courseLectureName, String folder) throws IOException {
        byte[] fileBytes = video.getBytes();
        String newVideoName = courseLectureName + "-video-" + System.currentTimeMillis() + video.getOriginalFilename();
        String savedFilePath = applicationBaseFolder + SLASH + folder + SLASH + newVideoName;
        Files.write(Paths.get(savedFilePath), fileBytes);
        return newVideoName;
    }

    public String moveCourseLectureFile(MultipartFile file, String lectureName, String folder) throws IOException {
        byte[] fileBytes = file.getBytes();
        String newFileName = lectureName + "-lecture-" + System.currentTimeMillis() + file.getOriginalFilename();
        String savedFilePath = applicationBaseFolder + SLASH + folder + SLASH + newFileName;
        Files.write(Paths.get(savedFilePath), fileBytes);
        return newFileName;
    }

    public void deleteCover(String path) throws IOException {
        Files.delete(Paths.get(applicationBaseFolder + SLASH + path));
    }
}
