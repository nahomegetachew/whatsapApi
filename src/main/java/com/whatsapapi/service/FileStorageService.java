package com.whatsapapi.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.whatsapapi.dtos.AttachmentDto;
import com.whatsapapi.entities.FileType;

@Service
public class FileStorageService {

    private static final String PICTURE_UPLOAD_DIR = "root/picture";
    private static final String DOCUMENT_UPLOAD_DIR = "root/document";
    private static final String VIDEO_UPLOAD_DIR = "root/video";

    public AttachmentDto storeFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String fileExtension = getFileExtension(fileName);
        String storedFileName = generateUniqueFileName(fileExtension);

        FileType fileType = getFileType(fileExtension);

        Path filePath;
        switch (fileType) {
            case IMAGE:
                filePath = Path.of(PICTURE_UPLOAD_DIR, storedFileName);
                break;
            case DOCUMENT:
                filePath = Path.of(DOCUMENT_UPLOAD_DIR, storedFileName);
                break;
            case VIDEO:
                filePath = Path.of(VIDEO_UPLOAD_DIR, storedFileName);
                break;
            default:
                throw new IllegalArgumentException("Invalid file type");
        }
        
        Path directoryPathPicures = Path.of(PICTURE_UPLOAD_DIR);
        createDirectoryIfNotExists(directoryPathPicures);
        
        Path directoryPathVideo = Path.of(VIDEO_UPLOAD_DIR);
        createDirectoryIfNotExists(directoryPathVideo);


        Path directoryPathDocument = Path.of(DOCUMENT_UPLOAD_DIR);
        createDirectoryIfNotExists(directoryPathDocument);
        
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        AttachmentDto attachmentDto = new AttachmentDto(fileType, storedFileName);
        return attachmentDto;
    }
    

    private void createDirectoryIfNotExists(Path directoryPath) throws IOException {
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }
    }
    

    private String getFileExtension(String fileName) {
        return StringUtils.getFilenameExtension(fileName);
    }

    private String generateUniqueFileName(String fileExtension) {
        return UUID.randomUUID() + "." + fileExtension;
    }

    private FileType getFileType(String fileExtension) {
        if (isImageFile(fileExtension)) {
            return FileType.IMAGE;
        } else if (isDocumentFile(fileExtension)) {
            return FileType.DOCUMENT;
        } else if (isVideoFile(fileExtension)) {
            return FileType.VIDEO;
        }
        throw new IllegalArgumentException("Invalid file type");
    }

    private boolean isImageFile(String fileExtension) {
        return fileExtension.equalsIgnoreCase("jpg") ||
                fileExtension.equalsIgnoreCase("jpeg") ||
                fileExtension.equalsIgnoreCase("png");
    }

    private boolean isDocumentFile(String fileExtension) {
        return fileExtension.equalsIgnoreCase("pdf") ||
                fileExtension.equalsIgnoreCase("docx") ||
                fileExtension.equalsIgnoreCase("doc") ||
                fileExtension.equalsIgnoreCase("txt");
        // Add more document file types as needed
    }

    private boolean isVideoFile(String fileExtension) {
        return fileExtension.equalsIgnoreCase("mp4") ||
                fileExtension.equalsIgnoreCase("mov") ||
                fileExtension.equalsIgnoreCase("avi");
    }
}