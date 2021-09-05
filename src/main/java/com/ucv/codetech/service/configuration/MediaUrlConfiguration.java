package com.ucv.codetech.service.configuration;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "service.media")
@Setter
public class MediaUrlConfiguration {
    private String baseUrl;
    private String folderUrl;
    private String uploadFileUrl;
    private String filePathVariableUrl;
    private String zipFilesUrl;
    private String folderPathVariableUrl;

    public String getFolderUrl() {
        return getBaseUrl() + folderUrl;
    }

    public String getUploadFileUrl() {
        return getBaseUrl() + uploadFileUrl;
    }

    public String getFilePathVariableUrl() {
        return getBaseUrl() + filePathVariableUrl;
    }

    public String getZipFilesUrl() {
        return getBaseUrl() + zipFilesUrl;
    }

    public String getFolderPathVariableUrl() {
        return getBaseUrl() + folderPathVariableUrl;
    }

    private String getBaseUrl() {
        return baseUrl;
    }
}
