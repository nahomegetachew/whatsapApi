package com.whatsapapi.dtos;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomDTO {
    private int id;
    private String name;
    
    private int maxMemberCount;
    
    private List<UserDTO> members;

    public ChatRoomDTO() {
        setMembers(new ArrayList<>());
    }

    public ChatRoomDTO(int id, String name, int maxMemberCount, List<UserDTO> members) {
        this.id = id;
        this.name = name;
        this.setMaxMemberCount(maxMemberCount);
        this.setMembers(members);
    }

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getMaxMemberCount() {
		return maxMemberCount;
	}

	public void setMaxMemberCount(int maxMemberCount) {
		this.maxMemberCount = maxMemberCount;
	}

	public List<UserDTO> getMembers() {
		return members;
	}

	public void setMembers(List<UserDTO> members) {
		this.members = members;
	}

}
