package com.printbloom.service;

import com.printbloom.config.FileStorageConfig;
import com.printbloom.exception.FileStorageException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    private final FileStorageConfig fileStorageConfig;

    public FileStorageService(FileStorageConfig fileStorageConfig) {
        this.fileStorageConfig = fileStorageConfig;
    }

    public void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new FileStorageException("Uploaded file is empty");
        }

        if (file.getSize() > fileStorageConfig.getMaximumFileSize()) {
            throw new FileStorageException("File size exceeds the allowed limit of " + fileStorageConfig.getMaximumFileSize() + " bytes");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.equals(fileStorageConfig.getAllowedFileTypes())) {
            throw new FileStorageException("Invalid file type. Only " + fileStorageConfig.getAllowedFileTypes() + " are allowed.");
        }
    }

    public String generateUniqueFilename(String originalFilename) {
        String extension = "";
        if (originalFilename != null && originalFilename.lastIndexOf(".") > 0) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String prefix = UUID.randomUUID().toString();
        
        String baseName = "upload";
        if (originalFilename != null) {
            baseName = originalFilename.replaceAll("[^a-zA-Z0-9.-]", "_").replace(extension, "");
        }

        return prefix + "_" + baseName + extension;
    }

    public Path storeFile(MultipartFile file) {
        validateFile(file);

        try {
            Path targetLocation = Paths.get(fileStorageConfig.getUploadDirectory()).toAbsolutePath().normalize();
            Files.createDirectories(targetLocation);

            String uniqueFilename = generateUniqueFilename(file.getOriginalFilename());
            Path targetFilePath = targetLocation.resolve(uniqueFilename);

            Files.copy(file.getInputStream(), targetFilePath, StandardCopyOption.REPLACE_EXISTING);

            return targetFilePath;

        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + file.getOriginalFilename() + ". Please try again!", ex);
        }
    }
}
