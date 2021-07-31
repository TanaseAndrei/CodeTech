package com.ucv.codetech.controller;

import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.facade.LectureFacade;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@RestController
@RequestMapping("/lectures")
@AllArgsConstructor
public class LectureController {

    private final LectureFacade lectureFacade;

    @PostMapping("/{id}/files")
    public void uploadLectureFiles(@PathVariable("id") Long lectureId, @RequestParam("files") MultipartFile[] multipartFiles) {
        lectureFacade.uploadLectureFiles(lectureId, multipartFiles);
    }

    @GetMapping(path = "/{lectureId}/zip-files")
    public ResponseEntity<Resource> zipLectureFiles(@PathVariable("lectureId") Long lectureId) {
        Resource resource = lectureFacade.zipLectureFiles(lectureId);
        MediaType mediaType = MediaTypeFactory.getMediaType(resource)
                .orElseThrow(() -> new AppException("The media type could not be determined", HttpStatus.BAD_REQUEST));
        return ResponseEntity.status(HttpStatus.OK).contentType(mediaType).headers(createHeader(resource)).body(resource);
    }

    @GetMapping(path = "/{lectureId}/file/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("lectureId") Long lectureId, @PathVariable("fileName") String fileName) {
        Resource resource = lectureFacade.downloadLectureFile(lectureId, fileName);
        MediaType mediaType = MediaTypeFactory.getMediaType(resource)
                .orElseThrow(() -> new AppException("The media type could not be determined", HttpStatus.BAD_REQUEST));
        return ResponseEntity.status(HttpStatus.OK).contentType(mediaType).headers(createHeader(resource)).body(resource);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLecture(@PathVariable("id") Long id) {
        lectureFacade.deleteLecture(id);
    }

    @DeleteMapping(path = "/{id}/file/{fileName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLectureFile(@PathVariable("id") Long lectureId, @PathVariable("fileName") String fileName) {
        lectureFacade.deleteLectureFile(lectureId, fileName);
    }

    private HttpHeaders createHeader(Resource resource) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_DISPOSITION, "attachment;filename=" + resource.getFilename());
        return httpHeaders;
    }
}
