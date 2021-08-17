package com.ucv.codetech.controller;

import com.ucv.codetech.controller.exception.AppException;
import com.ucv.codetech.controller.swagger.MediaApi;
import com.ucv.codetech.facade.MediaFacade;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@RestController
@RequestMapping("/media")
@AllArgsConstructor
public class MediaController implements MediaApi {

    private final MediaFacade mediaFacade;

    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'STUDENT')")
    @GetMapping(path = "/{folder}/file/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable("folder") String folder,
                                            @PathVariable("filename") String filename) {
        Resource resource = mediaFacade.getMediaAsResource(folder, filename);
        MediaType mediaType = getMediaType(resource);
        return ResponseEntity.status(HttpStatus.OK).contentType(mediaType).headers(createHeader(resource)).body(resource);
    }

    private MediaType getMediaType(Resource resource) {
        return MediaTypeFactory.getMediaType(resource)
                .orElseThrow(() -> new AppException("The media type could not be determined", HttpStatus.BAD_REQUEST));
    }

    private HttpHeaders createHeader(Resource resource) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_DISPOSITION, "attachment;filename=" + resource.getFilename());
        return httpHeaders;
    }
}
