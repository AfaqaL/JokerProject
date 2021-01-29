package com.joker.model.dto;

import com.joker.model.Room;
import com.joker.model.enums.RoomAction;

public class LobbyDTO {
    private long id;
    private RoomAction action;
    private Room room;
    private boolean full;

}
