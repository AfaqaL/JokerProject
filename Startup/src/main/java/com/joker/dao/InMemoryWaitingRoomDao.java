package com.joker.dao;

import com.joker.model.GameConstants;
import com.joker.model.GameMode;
import com.joker.model.Room;
import com.joker.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryWaitingRoomDao implements WaitingRoomDao {

    private static final Map<Long, Room> rooms = new ConcurrentHashMap<>();

    @Override
    public long createWaitingRoom(User user, String password, int bayonet, GameMode gameMode) {
        Room room = new Room();
        room.setId(rooms.size());
        room.setPassword(password);
        room.setBayonet(bayonet);
        room.setGameMode(gameMode);
        room.setPlayers(Collections.singletonList(user));

        rooms.put(room.getId(), room);
        return room.getId();
    }

    @Override
    public boolean addUser(User user, long roomId, String password) {
        Room room = rooms.get(roomId);

        if (room.getPassword() != null && !room.getPassword().equals(password)) {
            return false;
        }

        if (room.getPlayers().size() == GameConstants.MAX_PLAYERS) {
            return false;
        }

        synchronized (Room.class) {
            room.getPlayers().add(user);
        }

        return true;
    }

    @Override
    public List<Room> getAllRooms() {
        return new ArrayList<>(rooms.values());
    }

    @Override
    public boolean isRoomReady(long roomId) {
        return rooms.get(roomId).getPlayers().size() == GameConstants.MAX_PLAYERS;
    }

    @Override
    public Room removeRoom(long roomId) {
        Room result = rooms.get(roomId);
        rooms.remove(roomId);
        return result;
    }
}
