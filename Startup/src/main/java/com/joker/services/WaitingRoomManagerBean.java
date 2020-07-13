package com.joker.services;

import com.joker.model.*;

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
    public int createWaitingRoom(User user, String password, int bayonet, GameMode gameMode) {
        Room room = new Room();
        room.setId(rooms.size());
        room.setPassword(password);
        room.setBayonet(bayonet);
        room.setGameMode(gameMode);
        room.setPlayers(Collections.singletonList(user));

        rooms.put(room.getId(), room);

        return getIncreasedVersion();
    }

    @Override
    public int addUser(User user, long roomId, String password) {
        Room room = rooms.get(roomId);

        if (room.getPassword() != null && !room.getPassword().equals(password)) {
            return -1;
        }

        synchronized (Room.class) {
            if (room.getPlayers().size() == GameConstants.MAX_PLAYERS) {
                return -1;
            }
            room.getPlayers().add(user);
        }

        return getIncreasedVersion();
    }

    @Override
    public ListResult<Room> getAllRooms(int version) {
        boolean versionChanged = true;
        versionLock.lock();
        if (version == WaitingRoomManagerBean.version) {
            versionChanged = false;
        }
        version = WaitingRoomManagerBean.version;
        versionLock.unlock();

        if (!versionChanged) {
            return null;
        }

        ListResult<Room> result = new ListResult<>();
        result.setList(new ArrayList<>(rooms.values()));
        result.setVersion(version);
        return result;
    }

    @Override
    public Room startGame(long roomId, int version) {
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

    private int getIncreasedVersion() {
        int result;

        versionLock.lock();

        version++;
        result = version;

        versionLock.unlock();

        return result;
    }

    private boolean isVersionChanged(long version) {
        versionLock.lock();
        boolean res = (version == WaitingRoomManagerBean.version);
        versionLock.unlock();

        return res;
    }
}
