package com.whatsapapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whatsapapi.entities.ChatRoom;
import com.whatsapapi.entities.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findByChatroom(ChatRoom chatroom);

}