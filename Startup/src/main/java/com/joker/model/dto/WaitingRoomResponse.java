package com.joker.model.dto;

import com.joker.model.Room;

import java.util.List;

public class WaitingRoomResponse {

    private String isChanged;

    private List<Room> rooms;

    private long tableId;

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

    public long getTableId() {
        return tableId;
    }

    public void setTableId(long tableId) {
        this.tableId = tableId;
    }
}
