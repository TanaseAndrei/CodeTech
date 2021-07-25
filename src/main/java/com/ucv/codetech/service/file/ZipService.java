package com.ucv.codetech.service.file;

import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.ZipFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class ZipService {

    private static final String SLASH = File.separator;

    @Value("${application.base-folder}")
    private String applicationBaseFolder;

    public String zipFiles(List<String> fileNames, String courseName) throws IOException {
        String zipPath = applicationBaseFolder + SLASH + courseName + SLASH + "lectureName-" + System.currentTimeMillis() + ".zip";
        try (ZipFile zipFile = new ZipFile(zipPath)) {
            for (String fileName : fileNames) {
                String path = applicationBaseFolder + SLASH + courseName + SLASH + fileName;
                zipFile.addFile(path);
            }
        }
        return zipPath;
    }
}
