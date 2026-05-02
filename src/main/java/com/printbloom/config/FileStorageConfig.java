package com.printbloom.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileStorageConfig {

    @Value("${printbloom.filestorage.upload-directory:uploads}")
    private String uploadDirectory;

    @Value("${printbloom.filestorage.max-file-size-bytes:26214400}") // 25 MB default
    private long maximumFileSize;

    @Value("${printbloom.filestorage.allowed-file-types:application/pdf}")
    private String allowedFileTypes;

    public String getUploadDirectory() {
        return uploadDirectory;
    }

    public long getMaximumFileSize() {
        return maximumFileSize;
    }

    public String getAllowedFileTypes() {
        return allowedFileTypes;
    }
}

