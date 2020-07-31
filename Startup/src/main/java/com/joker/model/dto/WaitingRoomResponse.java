package com.joker.model.dto;

import com.joker.model.Room;

import java.util.List;

public class WaitingRoomResponse {

    private long tableId;

    private boolean changed;

    private List<Room> rooms;

    public long getTableId() {
        return tableId;
    }

    public void setTableId(long tableId) {
        this.tableId = tableId;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
