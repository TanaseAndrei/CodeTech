package com.ucv.codetech.service.file;

import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.ZipFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class ZipService {

    private static final String SLASH = File.separator;
    private static final String ZIP_EXTENSION = ".zip";

    @Value("${application.base-folder}")
    private String applicationBaseFolder;

    public String zipFiles(List<String> fileNames, String courseName) throws IOException {
        String zipPath = applicationBaseFolder + SLASH + courseName + SLASH + System.currentTimeMillis() + ZIP_EXTENSION;
        try (ZipFile zipFile = new ZipFile(zipPath)) {
            for (String filename : fileNames) {
                String filePathToZip = applicationBaseFolder + SLASH + courseName + SLASH + filename;
                zipFile.addFile(filePathToZip);
            }
        }
        return zipPath;
    }
}
