package com.ucv.codetech.facade;

import com.ucv.codetech.service.MediaService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;

import static com.ucv.codetech.StartupComponent.Facade;

@Facade
@AllArgsConstructor
public class MediaFacade {

    private final MediaService mediaService;

    public Resource getMediaAsResource(String folderName, String fileName) {
        return mediaService.getMediaAsResource(folderName, fileName);
    }
}
