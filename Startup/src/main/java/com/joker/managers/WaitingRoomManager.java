package com.joker.managers;

import com.joker.model.GameMode;
import com.joker.model.Room;
import com.joker.model.User;

import java.util.List;

public interface WaitingRoomManager {

    void createWaitingRoom(User user, String password, int bayonet, GameMode gameMode);

    boolean addUser(User user, long roomId, String password);

    List<Room> getAllRooms();

    boolean isRoomReady(long roomId);

    int getVersion();

    Room removeRoom(long roomId);
}
