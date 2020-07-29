package com.joker.dao.waitingroom;

import com.joker.game.Table;
import com.joker.model.enums.GameMode;
import com.joker.model.Room;
import com.joker.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Repository("waitingRoomDao")
public class InMemoryWaitingRoomDao implements WaitingRoomDao {

    private static final Map<Long, Room> rooms = new ConcurrentHashMap<>();

    private static final Map<Long, Room> readyRooms = new ConcurrentHashMap<>();

    private int version = 0;

    private final Lock lock = new ReentrantLock();

    @Override
    public long createWaitingRoom(User user, String password, int bayonet, GameMode gameMode) {
        Room room = new Room();
        room.setId(rooms.size());
        room.setPassword(password);
        room.setBayonet(bayonet);
        room.setGameMode(gameMode);

        List<User> players = new ArrayList<>();
        players.add(user);
        room.setPlayers(players);

        rooms.put(room.getId(), room);

        increaseVersion();
        return room.getId();
    }

    @Override
    public int getVersion() {
        return version;
    }

    @Override
    public synchronized boolean addUser(User user, long roomId, String password) {
        Room room = rooms.get(roomId);

        if (room.getPassword() != null && !room.getPassword().equals(password)) {
            return false;
        }

        if (room.getPlayers().size() == Table.NUM_PLAYERS) {
            return false;
        }

        room.getPlayers().add(user);

        increaseVersion();
        return true;
    }

    @Override
    public List<Room> getAllRooms() {
        return new ArrayList<>(rooms.values());
    }

    @Override
    public boolean isRoomReady(long roomId) {
        boolean ready = rooms.get(roomId).getPlayers().size() == Table.NUM_PLAYERS;

        if (ready) {
            Room room = rooms.get(roomId);
            rooms.remove(roomId);
            readyRooms.put(roomId, room);
        }

        return ready;
    }

    @Override
    public Room getReadyRoom(long roomId) {
        return readyRooms.get(roomId);
    }

    @Override
    public void removeRoom(long roomId) {
        readyRooms.remove(roomId);
    }

    private void increaseVersion() {
        lock.lock();
        version++;
        lock.unlock();
    }

    // For testing
    public Room getRoom(long roomId) {
        return rooms.get(roomId);
    }
}
