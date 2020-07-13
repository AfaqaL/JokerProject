package com.joker.services;

import com.joker.model.GameMode;
import com.joker.model.Room;
import com.joker.model.GameConstants;
import com.joker.model.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WaitingRoomManagerBean implements WaitingRoomManager {

    private static Map<Long, Room> rooms;

    private static int version;

    private static Lock versionLock;

    private static WaitingRoomManagerBean instance;

    private WaitingRoomManagerBean() {
        rooms = new ConcurrentHashMap<>();
        version = 0;
        versionLock = new ReentrantLock();
    }

    public static WaitingRoomManagerBean getInstance() {
        if (instance == null) {
            synchronized (WaitingRoomManagerBean.class) {
                if (instance == null) {
                    instance = new WaitingRoomManagerBean();
                }
            }
        }
        return instance;
    }

    @Override
    public void createWaitingRoom(User user, String password, int bayonet, GameMode gameMode) {
        Room room = new Room();
        room.setId(rooms.size());
        room.setPassword(password);
        room.setBayonet(bayonet);
        room.setGameMode(gameMode);
        room.setPlayers(Collections.singletonList(user));

        rooms.put(room.getId(), room);

        increaseVersion();
    }

    @Override
    public boolean addUser(User user, long roomId, String password) {
        Room room = rooms.get(roomId);

        if (room.getPassword() != null && !room.getPassword().equals(password)) {
            return false;
        }

        synchronized (Room.class) {
            if (room.getPlayers().size() == GameConstants.MAX_PLAYERS) {
                return false;
            }
            room.getPlayers().add(user);
        }

        increaseVersion();
        return true;
    }

    @Override
    public List<Room> getAllRooms(long version) {
        if (!isVersionChanged(version)) {
            return null;
        }
        return new ArrayList<>(rooms.values());
    }

    @Override
    public Room startGame(long roomId) {
        if (!isVersionChanged(version)) {
            return null;
        }

        Room room = rooms.get(roomId);
        synchronized (Room.class) {
            if (room.getPlayers().size() == GameConstants.MAX_PLAYERS) {
                return room;
            }
        }

        return null;
    }

    private void increaseVersion() {
        versionLock.lock();
        version++;
        versionLock.unlock();
    }

    private boolean isVersionChanged(long version) {
        versionLock.lock();
        boolean res = (version == WaitingRoomManagerBean.version);
        versionLock.unlock();

        return res;
    }
}
