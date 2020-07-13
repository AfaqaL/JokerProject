package com.joker.services;

import com.joker.model.GameMode;
import com.joker.model.Room;
import com.joker.model.User;

import java.util.List;

public interface WaitingRoomManager {

    void createWaitingRoom(User user, String password, int bayonet, GameMode gameMode);

    boolean addUser(User user, long roomId, String password);

    List<Room> getAllRooms(long versionId);

    Room startGame(long roomId);
}
