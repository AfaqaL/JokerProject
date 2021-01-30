package com.joker.model.dto;

import com.joker.model.Room;
import com.joker.model.enums.RoomAction;

public class LobbyDTO {
    private long id;
    private RoomAction action;
    private Room room;
    private boolean full;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RoomAction getAction() {
        return action;
    }

    public void setAction(RoomAction action) {
        this.action = action;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public boolean isFull() {
        return full;
    }

    public void setFull(boolean full) {
        this.full = full;
    }

}
