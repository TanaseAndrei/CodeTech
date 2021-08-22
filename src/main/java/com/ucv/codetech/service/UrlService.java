package com.ucv.codetech.service;

import lombok.NoArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;

import java.net.URI;

@Service
@NoArgsConstructor
public class UrlService {

    public static final String ZIP_URL = "http://localhost:8081/media/{folder}/zip-files";
    public static final String MEDIA_FOLDER_FILE_PATH_VARIABLE_URL = "http://localhost:8081/media/{folder}/{filename}";
    public static final String MEDIA_FOLDER_URL = "http://localhost:8081/media/folder";
    public static final String MEDIA_FOLDER_PATH_VARIABLE_URL = "http://localhost:8081/media/{folder}";
    public static final String MEDIA_FOLDER_NAME_PATH_VARIABLE_URL = "http://localhost:8081/media/folder/{folder}";
    public static final String MEDIA_FOLDER_PATH_VARIABLE_RENAME_URL = "http://localhost:8081/media/folder/{folderName}/rename";

    public Link getLinkForZippingFiles(String folderName) {
        URI uri = new UriTemplate(ZIP_URL).expand(folderName);
        return Link.of(String.valueOf(uri), "zip-files");
    }

    public Link getLinkForGettingMedia(String folder, String fileName, String rel) {
        URI uri = new UriTemplate(MEDIA_FOLDER_FILE_PATH_VARIABLE_URL).expand(folder, fileName);
        return Link.of(String.valueOf(uri), rel);
    }
}
