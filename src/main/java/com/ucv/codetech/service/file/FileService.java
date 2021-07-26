package com.ucv.codetech.service.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@Slf4j
public class FileService {

    private static final String SLASH = File.separator;

    @Value("${application.base-folder}")
    private String applicationBaseFolder;

    public String createCourseFolder(String courseName) throws IOException {
        if (!Files.exists(Paths.get(applicationBaseFolder + courseName))) {
            Files.createDirectory(Paths.get(applicationBaseFolder + SLASH + courseName));
        }
        return courseName;
    }

    public String moveFile(MultipartFile multipartFile, String courseName, String folder) throws IOException {
        byte[] fileBytes = multipartFile.getBytes();
        String newFilename = courseName + "-cover-" + System.currentTimeMillis() + multipartFile.getOriginalFilename();
        String savedFilePath = applicationBaseFolder + SLASH + folder + SLASH + newFilename;
        Files.write(Paths.get(savedFilePath), fileBytes);
        return newFilename;
    }

    public void deleteCover(String filename, String folder) throws IOException {
        Files.delete(Paths.get(applicationBaseFolder + SLASH + folder + SLASH + filename));
    }

    public void deleteCourseLectureVideos(List<String> fileNames, String folder) throws IOException {
        for (String courseLectureVideo : fileNames) {
            Files.delete(Paths.get(applicationBaseFolder + SLASH + folder + SLASH + courseLectureVideo));
        }
    }

    public void deleteCourseLectureFiles(List<String> filenames, String folder) throws IOException {
        for (String courseLectureFile : filenames) {
            Files.delete(Paths.get(applicationBaseFolder + SLASH + folder + SLASH + courseLectureFile));
        }
    }

    public void deleteBaseFolder(String folder) throws IOException {
        Files.delete(Paths.get(applicationBaseFolder + SLASH + folder));
    }

    public Resource getFileAsResource(String folder, String fileName) throws MalformedURLException {
        Path path = Paths.get(applicationBaseFolder + SLASH + folder + SLASH + fileName);
        return new UrlResource(path.toUri());
    }
}
