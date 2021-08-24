package com.ucv.codetech.service;

import com.ucv.codetech.controller.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriTemplate;

import java.util.List;

import static com.ucv.codetech.service.UrlService.MEDIA_FOLDER_NAME_PATH_VARIABLE_URL;

@Service
@Slf4j
public class MediaRestClientService {

    private final RestTemplate restTemplate;

    public MediaRestClientService() {
        this.restTemplate = new RestTemplate();
    }

    public String createFolder(String name) {
        ResponseEntity<String> response =
                restTemplate.postForEntity(new UriTemplate(UrlService.MEDIA_FOLDER_URL).expand(), new HttpEntity<>(name, getApplicationJsonHeader()), String.class);
        return response.getBody();
    }

    @Async
    public void deleteFolder(String folder) {
        restTemplate.exchange(new UriTemplate(MEDIA_FOLDER_NAME_PATH_VARIABLE_URL).expand(folder),
                HttpMethod.DELETE, new HttpEntity<>(getApplicationJsonHeader()), void.class);
    }

    @Async
    public void deleteFile(String folder, String fileName) {
        restTemplate.delete(new UriTemplate(UrlService.MEDIA_FOLDER_FILE_PATH_VARIABLE_URL).expand(folder, fileName));
    }

    @Async
    public void updateCourseFolder(String oldFolderName, String newFolderName) {
        ResponseEntity<Boolean> response = restTemplate.exchange(new UriTemplate(UrlService.MEDIA_FOLDER_PATH_VARIABLE_RENAME_URL).expand(oldFolderName),
                HttpMethod.PUT, new HttpEntity<>(newFolderName, getApplicationJsonHeader()), Boolean.class);
        Boolean success = response.getBody();
        if (success == null || !success) {
            log.error("Couldn't update the folder of the course {}", newFolderName);
            throw new AppException("Course folder could not have been updated to a new name", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Async
    public void deleteFiles(String folder, List<String> fileNames) {
        restTemplate.exchange(new UriTemplate(UrlService.MEDIA_FOLDER_PATH_VARIABLE_URL).expand(folder),
                HttpMethod.DELETE, new HttpEntity<>(fileNames), void.class);
    }

    public String addFileToFolder(String folder, MultipartFile file) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file.getResource());
        ResponseEntity<String> response = restTemplate.postForEntity(new UriTemplate(MEDIA_FOLDER_NAME_PATH_VARIABLE_URL + "/file").expand(folder),
                new HttpEntity<>(body, getMultipartFormDataHeader()), String.class);
        return response.getBody();
    }

    private HttpHeaders getApplicationJsonHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private HttpHeaders getMultipartFormDataHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        return headers;
    }
}
