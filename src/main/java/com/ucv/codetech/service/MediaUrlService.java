package com.ucv.codetech.service;

import com.ucv.codetech.service.configuration.MediaUrlConfiguration;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;

import java.net.URI;

@Service
@AllArgsConstructor
public class MediaUrlService {

    private final MediaUrlConfiguration mediaUrlConfiguration;

    public Link getLinkForZippingFiles(String folderName) {
        URI uri = new UriTemplate(mediaUrlConfiguration.getZipFilesUrl()).expand(folderName);
        return Link.of(String.valueOf(uri), "zip-files");
    }

    public Link getLinkForGettingMedia(String folder, String fileName, String rel) {
        URI uri = new UriTemplate(mediaUrlConfiguration.getFilePathVariableUrl()).expand(folder, fileName);
        return Link.of(String.valueOf(uri), rel);
    }
}
