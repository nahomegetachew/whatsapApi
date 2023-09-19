package com.whatsapapi.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.whatsapapi.dtos.ChatRoomDTO;
import com.whatsapapi.dtos.UserDTO;
import com.whatsapapi.entities.ChatRoom;
import com.whatsapapi.entities.User;
import com.whatsapapi.repository.ChatRoomRepository;
import com.whatsapapi.repository.UserRepository;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private UserRepository userRepository;

    public ChatRoom createChatRoom(ChatRoomDTO chatRoomDTO, String username) {
        
        // Retrieve the creator user by their ID
        Optional<User> creatorUserOptional = userRepository.findByUsername(username);
        if (creatorUserOptional.isEmpty()) {
            throw new IllegalArgumentException("Creator user not found.");
        }
        User creatorUser = creatorUserOptional.get();

        // Create the chatroom entity
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setName(chatRoomDTO.getName());
        chatRoom.setMaxMemberCount(chatRoomDTO.getMaxMemberCount());
        chatRoom.getMembers().add(creatorUser);

        // Save the chatroom to the database
        chatRoom = chatRoomRepository.save(chatRoom);

        // Convert and return the created chatroom DTO
        return chatRoom;
    }
    
    public boolean leaveChatroom(Integer chatroomId, String username) {        
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + username));
        
        ChatRoom chatroom = chatRoomRepository.findById(chatroomId)
                .orElseThrow(() -> new EntityNotFoundException("Chatroom not found with ID: " + chatroomId));

        user.leaveChatroom(chatroom); // Assuming the leaveChatroom method is implemented in UserEntity

        userRepository.save(user);
        chatRoomRepository.save(chatroom);
        return true;
    }
    
    public ChatRoom saveChatroom(ChatRoom chatroom) {
        return chatRoomRepository.save(chatroom);
    }
    
	private ChatRoomDTO convertToChatRoomDTO(ChatRoom chatRoom) {
        ChatRoomDTO chatRoomDTO = new ChatRoomDTO();
        chatRoomDTO.setId(chatRoom.getId());
        chatRoomDTO.setName(chatRoom.getName());
        chatRoomDTO.setMaxMemberCount(chatRoom.getMaxMemberCount());

        List<UserDTO> memberDTOs = chatRoom.getMembers()
                .stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toList());
        chatRoomDTO.setMembers(memberDTOs);

        return chatRoomDTO;
    }

    private UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        return userDTO;
    }

	public ChatRoom getChatroomById(int chatroomId) {
        return chatRoomRepository.findById(chatroomId)
                .orElseThrow(() -> new NoSuchElementException("User not found with username: " + chatroomId));
	}
}
