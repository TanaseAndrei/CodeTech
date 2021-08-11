package com.ucv.codetech.controller;

import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.controller.swagger.LectureApi;
import com.ucv.codetech.facade.LectureFacade;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@RestController
@RequestMapping("/lectures")
@AllArgsConstructor
public class LectureController implements LectureApi {

    private final LectureFacade lectureFacade;

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @PatchMapping("/{id}/files")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void uploadLectureFiles(@PathVariable("id") Long lectureId, @RequestParam("files") MultipartFile[] multipartFiles) {
        lectureFacade.uploadFiles(lectureId, multipartFiles);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping(path = "/{lectureId}/zip-files")
    public ResponseEntity<Resource> zipLectureFiles(@PathVariable("lectureId") Long lectureId) {
        Resource resource = lectureFacade.zipFiles(lectureId);
        MediaType mediaType = MediaTypeFactory.getMediaType(resource)
                .orElseThrow(() -> new AppException("The media type could not be determined", HttpStatus.INTERNAL_SERVER_ERROR));
        return ResponseEntity.status(HttpStatus.OK).contentType(mediaType).headers(createHeader(resource)).body(resource);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping(path = "/{lectureId}/file/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("lectureId") Long lectureId, @PathVariable("fileName") String fileName) {
        Resource resource = lectureFacade.downloadFile(lectureId, fileName);
        MediaType mediaType = MediaTypeFactory.getMediaType(resource)
                .orElseThrow(() -> new AppException("The media type could not be determined", HttpStatus.INTERNAL_SERVER_ERROR));
        return ResponseEntity.status(HttpStatus.OK).contentType(mediaType).headers(createHeader(resource)).body(resource);
    }

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLecture(@PathVariable("id") Long id) {
        lectureFacade.delete(id);
    }

    @PreAuthorize("hasRole('INSTRUCTOR')")
    @DeleteMapping(path = "/{id}/file/{fileName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLectureFile(@PathVariable("id") Long lectureId, @PathVariable("fileName") String fileName) {
        lectureFacade.deleteFile(lectureId, fileName);
    }

    private HttpHeaders createHeader(Resource resource) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_DISPOSITION, "attachment;filename=" + resource.getFilename());
        return httpHeaders;
    }
}
