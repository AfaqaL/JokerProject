package com.joker.model.dto;

import com.joker.model.Room;

public class CreateRoom {
    private UserDTO user;
    private Room room;

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
