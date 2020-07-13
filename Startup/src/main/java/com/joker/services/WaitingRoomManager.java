package com.joker.services;

import com.joker.model.GameMode;
import com.joker.model.ListResult;
import com.joker.model.Room;
import com.joker.model.User;

public interface WaitingRoomManager {

    int createWaitingRoom(User user, String password, int bayonet, GameMode gameMode);

    int addUser(User user, long roomId, String password);

    ListResult<Room> getAllRooms(int version);

    Room startGame(long roomId, int version);
}
