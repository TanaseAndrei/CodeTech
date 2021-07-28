package com.ucv.codetech;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@Slf4j
public class StartupComponent implements CommandLineRunner {


    @Value("${application.base-folder}")
    private String applicationBaseFolder;

    @Override
    public void run(String... args) throws Exception {
        createOutputFolders();
    }

    private void createOutputFolders() throws IOException {
        Path path = Paths.get(applicationBaseFolder);
        if (!Files.exists(path)) {
            log.info("Created base output folder: {}", applicationBaseFolder);
            Files.createDirectory(path);
        }
    }
}
