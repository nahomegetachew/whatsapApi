package com.whatsapapi.dao.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequestDto {
    private int chatroomId;
    private String content;
    private MultipartFile file;

    // Getters and Setters
}