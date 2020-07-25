package com.joker.dao.waitingroom;

import com.joker.model.GameMode;
import com.joker.model.Room;
import com.joker.model.User;

import java.util.List;

public interface WaitingRoomDao {

    long createWaitingRoom(User user, String password, int bayonet, GameMode gameMode);

    boolean addUser(User user, long roomId, String password);

    List<Room> getAllRooms();

    boolean isRoomReady(long roomId);

    Room removeRoom(long roomId);
}
