package com.whatsapapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whatsapapi.dao.request.CreatChatroomRequest;
import com.whatsapapi.dao.request.SignUpRequest;
import com.whatsapapi.dtos.ChatRoomDTO;
import com.whatsapapi.entities.ChatRoom;
import com.whatsapapi.service.ChatRoomService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/chatrooms")
@RequiredArgsConstructor
public class ChatroomController {

    @Autowired
    private ChatRoomService chatroomService;


    @PostMapping("/create")
    @PreAuthorize("hasAuthority('USER')")
    public String createChatroom(@RequestBody CreatChatroomRequest creatChatroomRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
    	ChatRoomDTO chatroom = new ChatRoomDTO();
    	chatroom.setName(creatChatroomRequest.getName());
    	chatroom.setMaxMemberCount(creatChatroomRequest.getMaxMemberCount());
    	ChatRoom chatRoom = chatroomService.createChatRoom(chatroom, username);
    	if(chatRoom == null) {
    		return "canot create Chatroom";
    	}
        return "chat room created successfully";
    }

    
    @GetMapping("/leave/{chatroomId}")
    public ResponseEntity<String> leaveChatroom(@Valid @PathVariable Integer chatroomId) {
        // Retrieve the authenticated user information
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        System.out.println(chatroomId.toString());
        boolean leaved = chatroomService.leaveChatroom(chatroomId, username);
        return ResponseEntity.ok("You have left the chatroom." + leaved);
    }

  
}