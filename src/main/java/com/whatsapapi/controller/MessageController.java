package com.whatsapapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.whatsapapi.entities.Message;
import com.whatsapapi.service.ChatRoomService;
import com.whatsapapi.service.MessageService;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {

	@Autowired
    private MessageService messageService;


    // ... other methods

    // Send message with content and optional file
    @PostMapping("/send/{chatroomId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> sendMessageWithContent(
            @PathVariable int chatroomId,
            @RequestParam(required = false) MultipartFile file,
            @RequestParam String content) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.print("got hear");
//    	int chatroomId, String content,String username, MultipartFile file
    	try {
			messageService.sendMessageWithContentAndFile(chatroomId, content,username, file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return ResponseEntity.ok("Message sent successfully");
    }
    
    // Send message with content and optional file
    @GetMapping("/read/{chatroomId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<Message>> readMessageWithContent(
            @PathVariable int chatroomId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return ResponseEntity.ok(messageService.readMessagesInRoom(chatroomId,username));
    }
}