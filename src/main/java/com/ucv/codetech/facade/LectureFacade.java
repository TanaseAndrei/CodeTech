package com.ucv.codetech.facade;

import com.ucv.codetech.StartupComponent.Facade;
import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.model.Lecture;
import com.ucv.codetech.service.LectureService;
import com.ucv.codetech.service.UrlService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.ucv.codetech.service.UrlService.MEDIA_FOLDER_NAME_PATH_VARIABLE_URL;

@Facade
@AllArgsConstructor
@Slf4j
public class LectureFacade {

    private final LectureService lectureService;

    @Transactional
    public void delete(Long id) {
        List<String> filesToDelete = new ArrayList<>(lectureService.getLectureFiles(id));
        filesToDelete.add(lectureService.getLectureVideo(id));
        String folder = lectureService.getAssociatedCourseFolder(id);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(new UriTemplate(UrlService.MEDIA_FOLDER_PATH_VARIABLE_URL).expand(folder),
                HttpMethod.DELETE, new HttpEntity<>(filesToDelete), void.class);
        lectureService.deleteLecture(id);
        log.info("Deleted lecture with id {}", id);
    }

    @Transactional
    public void uploadFiles(Long id, MultipartFile[] multipartFiles) {
        Lecture lecture = lectureService.findById(id);
        String folder = lectureService.getAssociatedCourseFolder(id);
        lecture.setLectureFileNames(sendFilesToFolder(multipartFiles, folder));
        lectureService.saveOrUpdate(lecture);
        log.info("Uploading files to lecture {}", id);
    }

    @Transactional
    public void deleteFile(Long lectureId, String fileName) {
        log.info("Deleting the file {} from the lecture {}", fileName, lectureId);
        String folder = lectureService.getAssociatedCourseFolder(lectureId);
        Lecture lecture = lectureService.findById(lectureId);
        if (!lecture.containsLectureFile(fileName)) {
            log.warn("The file {} does not exist in the lecture", fileName);
            throw new AppException("The file doesn't exist in the lecture", HttpStatus.NOT_FOUND);
        }
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(new UriTemplate(UrlService.MEDIA_FOLDER_FILE_PATH_VARIABLE_URL).expand(folder, fileName));
        lecture.deleteLectureFile(fileName);
        lectureService.saveOrUpdate(lecture);
        log.info("Deleted the file {} from the lecture {}", fileName, lectureId);

    }

    private List<String> sendFilesToFolder(MultipartFile[] multipartFiles, String courseFolder) {
        List<String> fileNames = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        for (MultipartFile multipartFile : multipartFiles) {
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", multipartFile.getResource());
            ResponseEntity<String> response = restTemplate.postForEntity(new UriTemplate(MEDIA_FOLDER_NAME_PATH_VARIABLE_URL + "/file").expand(courseFolder),
                    new HttpEntity<>(body, headers), String.class);
            fileNames.add(response.getBody());
        }
        return fileNames;
    }
}
