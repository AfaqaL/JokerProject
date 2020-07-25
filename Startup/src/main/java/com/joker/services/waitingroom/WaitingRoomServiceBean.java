package com.joker.services.waitingroom;

import com.joker.dao.waitingroom.WaitingRoomDao;
import com.joker.model.GameMode;
import com.joker.model.Room;
import com.joker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WaitingRoomServiceBean implements WaitingRoomService {

    @Autowired
    private WaitingRoomDao waitingRooms;

    @Override
    public long createWaitingRoom(User user, String password, int bayonet, GameMode gameMode) {
        return waitingRooms.createWaitingRoom(user, password, bayonet, gameMode);
    }

    @Override
    public boolean addUser(User user, long roomId, String password) {
        return waitingRooms.addUser(user, roomId, password);
    }

    @Override
    public List<Room> getAllRooms() {
        return waitingRooms.getAllRooms();
    }

    @Override
    public boolean isRoomReady(long roomId) {
        return waitingRooms.isRoomReady(roomId);
    }

    @Override
    public int getVersion() {
        return waitingRooms.getVersion();
    }

    @Override
    public Room getReadyRoom(long roomId) {
        return waitingRooms.getReadyRoom(roomId);
    }

    @Override
    public void removeRoom(long roomId) {
        waitingRooms.removeRoom(roomId);
    }
}
