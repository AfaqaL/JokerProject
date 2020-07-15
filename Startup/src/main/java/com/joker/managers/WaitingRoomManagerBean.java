package com.joker.managers;

import com.joker.dao.InMemoryWaitingRoomDao;
import com.joker.dao.WaitingRoomDao;
import com.joker.model.GameMode;
import com.joker.model.Room;
import com.joker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class WaitingRoomManagerBean implements WaitingRoomManager {

    private final WaitingRoomDao waitingRooms;

    private static int version;

    private static Lock versionLock;

    @Autowired
    public WaitingRoomManagerBean(InMemoryWaitingRoomDao waitingRooms) {
        version = 0;
        versionLock = new ReentrantLock();
        this.waitingRooms = waitingRooms;
    }

    @Override
    public long createWaitingRoom(User user, String password, int bayonet, GameMode gameMode) {
        long roomId = waitingRooms.createWaitingRoom(user, password, bayonet, gameMode);
        increaseVersion();
        return roomId;
    }

    @Override
    public boolean addUser(User user, long roomId, String password) {
        boolean userAdded = waitingRooms.addUser(user, roomId, password);
        if (userAdded) {
            increaseVersion();
        }

        return userAdded;
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
        int result;
        versionLock.lock();
        result = version;
        versionLock.unlock();

        return result;
    }

    @Override
    public Room removeRoom(long roomId) {
        return waitingRooms.removeRoom(roomId);
    }

    private void increaseVersion() {
        versionLock.lock();
        version++;
        versionLock.unlock();
    }
}
