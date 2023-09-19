package com.whatsapapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.whatsapapi.dao.request.MessageRequestDto;
import com.whatsapapi.dtos.AttachmentDto;
import com.whatsapapi.entities.Attachement;
import com.whatsapapi.entities.ChatRoom;
import com.whatsapapi.entities.Message;
import com.whatsapapi.entities.User;
import com.whatsapapi.repository.MessageRepository;
import com.whatsapapi.repository.UserRepository;

@Service
public class MessageService {

	@Autowired
    private ChatRoomService chatroomService;
	@Autowired
    private UserService userService;
	@Autowired
    private FileStorageService fileStorageService;
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private MessageRepository messageRepository;


//    public void sendMessage(MessageRequestDto messageRequest,String username) throws Exception {
//        validateMessageRequest(messageRequest);
//
//        User sender = userService.getUserByUsername(username);
//        ChatRoom chatroom = chatroomService.getChatroomById(messageRequest.getChatroomId());
//
//        validateChatroomMembership(sender, chatroom);
//
//        // Perform the actual message sending logic using the WhatsApp API or any other integration
//
//        Message message = new Message();
//        // Save the message using the appropriate repository or ORM method
//
//        if (messageRequest.getFile() != null) {
//            String filePath = fileStorageService.storeFile(messageRequest.getFile());
//            message.setFilePath(filePath);
//        }
//
//        // Perform any other necessary operations
//
//    }
    

    public void sendMessageWithContentAndFile(int chatroomId, String content,String username, MultipartFile file) throws Exception {
//        ChatRoom chatroom = chatroomRepository.findById(chatroomId)
//                .orElseThrow(() -> new NotFoundException("Chatroom not found"));

      User sender = userService.getUserByUsername(username);
      ChatRoom chatroom = chatroomService.getChatroomById(chatroomId);

      validateChatroomMembership(sender, chatroom);
        
        Message message = new Message();
        

        if (file != null && !file.isEmpty()) {
            AttachmentDto fileDetail = fileStorageService.storeFile(file);
            Attachement attachment = new Attachement();
            attachment.setFileName(fileDetail.getFileName());
            attachment.setFileType(fileDetail.getFileType());
            message.setAttachement(attachment);
        }
        message.setChatroom(chatroom);


        messageRepository.save(message);
    }
    
    public List<Message> readMessagesInRoom(int chatroomId,String username) {
        User reader = userService.getUserByUsername(username);
        ChatRoom chatroom = chatroomService.getChatroomById(chatroomId);

        try {
			validateChatroomMembership(reader, chatroom);
		} catch (Exception e) {
			e.printStackTrace();
		}
        System.out.print(messageRepository.findByChatroom(chatroom));
        return messageRepository.findByChatroom(chatroom);
    }

//    private void validateMessageRequest(MessageRequestDto messageRequest) throws Exception {
//        if (messageRequest.getSenderId() == null || messageRequest.getRecipientId() == null || messageRequest.getChatroomId() == null) {
//            throw new IllegalArgumentException("Invalid message request: missing required parameters");
//        }
//        // Add any other validation rules as per your requirements
//    }

    private void validateChatroomMembership(User sender, ChatRoom chatroom) throws Exception {
        if (!chatroom.getMembers().contains(sender)) {
            throw new Exception("Sender is not a member of the chatroom");
        }
    }
}