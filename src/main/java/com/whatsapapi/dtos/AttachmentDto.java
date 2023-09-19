package com.whatsapapi.dtos;

import com.whatsapapi.entities.FileType;

public class AttachmentDto {
    private FileType fileType;
    private String fileName;
    
    public AttachmentDto(FileType fileType, String fileName) {
        this.fileType = fileType;
        this.fileName = fileName;
    }

    // getters and setters
	public FileType getFileType() {
		return fileType;
	}
	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}