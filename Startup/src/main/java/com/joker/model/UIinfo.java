package com.joker.model;

import java.util.List;

public class UIinfo {
    private String isChanged;
    private List<Room> rooms;
    private Long myId;

    public String getIsChanged() {
        return isChanged;
    }

    public void setIsChanged(String isChanged) {
        this.isChanged = isChanged;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public long getMyId() {
        return myId;
    }

    public void setMyId(long myId) {
        this.myId = myId;
    }
}
