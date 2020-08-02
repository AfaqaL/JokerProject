package dao;

import com.joker.dao.waitingroom.InMemoryWaitingRoomDao;
import com.joker.model.Room;
import com.joker.model.User;
import com.joker.model.enums.GameMode;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class InMemoryWaitingRoomDaoTest {

    private InMemoryWaitingRoomDao waitingRoom;

    private static User user1, user2, user3, user4, user5;

    private static String password;

    private static int bayonet;

    private static GameMode mode;

    @BeforeClass
    public static void setUp() {
        user1 = new User(1, "name1", "mail1", "pass1", 10);
        user2 = new User(2, "name2", "mail2", "pass2", 20);
        user3 = new User(3, "name3", "mail3", "pass3", 30);
        user4 = new User(4, "name4", "mail4", "pass4", 40);
        user5 = new User(5, "name5", "mail5", "pass5", 50);

        password = "room";
        bayonet = 200;
        mode = GameMode.NINES;
    }

    @Before
    public void createWaitingRoomInstance() {
        waitingRoom = new InMemoryWaitingRoomDao();
    }

    @Test
    public void testCreateWaitingRoom() {
        assertEquals(0, waitingRoom.getVersion());

        long roomId = waitingRoom.createWaitingRoom(user1, password, bayonet, mode);
        assertTrue(roomId >= 0);

        Room actual = waitingRoom.getRoom(roomId);
        assertEquals(roomId, actual.getId());
        assertEquals(password, actual.getPassword());
        assertEquals(bayonet, actual.getBayonet());
        assertEquals(mode, actual.getGameMode());

        assertNotNull(actual.getPlayers());
        assertEquals(1, actual.getPlayers().size());
        assertEquals(user1, actual.getPlayers().get(0));

        assertEquals(1, waitingRoom.getVersion());
    }

    @Test
    public void testAddUserIncorrectPassword() {
        long roomId = waitingRoom.createWaitingRoom(user1, password, bayonet, mode);
        assertFalse(waitingRoom.addUser(user2, roomId, "incorrect"));
    }

    @Test
    public void testAddUserWithoutPassword() {
        long roomId = waitingRoom.createWaitingRoom(user1, null, bayonet, mode);
        assertTrue(waitingRoom.addUser(user1, roomId, null));
    }

    @Test
    public void testAddUserWhenRoomIsFull() {
        assertEquals(0, waitingRoom.getVersion());

        long roomId = waitingRoom.createWaitingRoom(user1, password, bayonet, mode);
        assertEquals(1, waitingRoom.getVersion());

        assertTrue(waitingRoom.addUser(user2, roomId, password));
        assertEquals(2, waitingRoom.getVersion());

        assertTrue(waitingRoom.addUser(user3, roomId, password));
        assertEquals(3, waitingRoom.getVersion());

        assertTrue(waitingRoom.addUser(user4, roomId, password));
        assertEquals(4, waitingRoom.getVersion());

        assertFalse(waitingRoom.addUser(user5, roomId, password));
        assertEquals(4, waitingRoom.getVersion());
    }

    @Test
    public void testGetAllRooms() {
        long roomId1 = waitingRoom.createWaitingRoom(user1, password, bayonet, mode);
        waitingRoom.addUser(user2, roomId1, password);

        long roomId2 = waitingRoom.createWaitingRoom(user3, null, 100, GameMode.STANDARD);
        waitingRoom.addUser(user4, roomId2, null);

        waitingRoom.createWaitingRoom(user5, "pass", 150, GameMode.STANDARD);

        assertEquals(3, waitingRoom.getAllRooms().size());
    }

    @Test
    public void testIsRoomReadyReturnsFalse() {
        long roomId = waitingRoom.createWaitingRoom(user1, password, bayonet, mode);
        assertFalse(waitingRoom.isRoomReady(roomId));

        waitingRoom.addUser(user2, roomId, password);
        assertFalse(waitingRoom.isRoomReady(roomId));
    }

    @Test
    public void testReadyRoom() {
        long roomId = waitingRoom.createWaitingRoom(user1, password, bayonet, mode);
        assertFalse(waitingRoom.isRoomReady(roomId));
        assertNull(waitingRoom.getReadyRoom(roomId));

        waitingRoom.addUser(user2, roomId, password);
        assertFalse(waitingRoom.isRoomReady(roomId));
        assertNull(waitingRoom.getReadyRoom(roomId));

        waitingRoom.addUser(user3, roomId, password);
        assertFalse(waitingRoom.isRoomReady(roomId));
        assertNull(waitingRoom.getReadyRoom(roomId));

        assertNotNull(waitingRoom.getRoom(roomId));

        waitingRoom.addUser(user4, roomId, password);
        assertTrue(waitingRoom.isRoomReady(roomId));
        assertNotNull(waitingRoom.getReadyRoom(roomId));
        assertNotNull(waitingRoom.getRoom(roomId));

        waitingRoom.removeRoom(roomId);
        assertNull(waitingRoom.getReadyRoom(roomId));
    }

    @Test
    public void testRemoveUser() {
        long roomId = waitingRoom.createWaitingRoom(user1, password, bayonet, mode);

        waitingRoom.addUser(user2, roomId, password);

        assertTrue(waitingRoom.removeUser(user1, roomId));
        assertFalse(waitingRoom.removeUser(user2, roomId));
    }

    @Test
    public void testConcurrentAddUser() throws InterruptedException {
        long roomId = waitingRoom.createWaitingRoom(user1, password, bayonet, mode);

        Thread t1 = new Thread(() -> waitingRoom.addUser(user2, roomId, password));
        Thread t2 = new Thread(() -> waitingRoom.addUser(user3, roomId, password));

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        Room room = waitingRoom.getRoom(roomId);
        assertEquals(3, waitingRoom.getVersion());
        assertEquals(3, room.getPlayers().size());

        Thread t3 = new Thread(() -> waitingRoom.addUser(user4, roomId, password));
        Thread t4 = new Thread(() -> waitingRoom.addUser(user5, roomId, password));

        t3.start();
        t4.start();

        t3.join();
        t4.join();

        assertEquals(4, waitingRoom.getVersion());

        assertNotNull(waitingRoom.getRoom(roomId));
        assertNull(waitingRoom.getReadyRoom(roomId));

        assertTrue(waitingRoom.isRoomReady(roomId));

        assertNotNull(waitingRoom.getReadyRoom(roomId));
    }
}
