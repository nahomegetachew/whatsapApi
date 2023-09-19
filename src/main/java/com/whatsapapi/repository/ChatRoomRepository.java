package com.whatsapapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whatsapapi.entities.ChatRoom;


@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {
    Optional<ChatRoom> findByIdAndMembers_Id(Integer chatRoomId, Integer memberId);
    Optional<ChatRoom> findById(Integer chatRoomId);
    List<ChatRoom> findByMembers_Id(Integer memberId);
}