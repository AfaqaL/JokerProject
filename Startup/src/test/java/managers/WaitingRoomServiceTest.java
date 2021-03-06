package managers;

import com.joker.dao.waitingroom.InMemoryWaitingRoomDao;
import com.joker.services.waitingroom.WaitingRoomServiceBean;
import com.joker.model.enums.GameMode;
import com.joker.model.Room;
import com.joker.model.User;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WaitingRoomServiceTest {

    @InjectMocks
    private WaitingRoomServiceBean waitingRoomManager;

    @Mock
    private InMemoryWaitingRoomDao waitingRooms;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @Captor
    private ArgumentCaptor<String> passwordCaptor;

    @Captor
    private ArgumentCaptor<Integer> bayonetCaptor;

    @Captor
    private ArgumentCaptor<GameMode> gameModeCaptor;

    @Captor
    private ArgumentCaptor<Long> roomIdCaptor;

    private static User user;

    private static String password;

    private static int bayonet;

    private static GameMode gameMode;

    @BeforeClass
    public static void setUp() {
        user = new User(1, "username", "mail", "password", 100);
        password = "password";
        bayonet = 200;
        gameMode = GameMode.NINES;
    }

    @Test
    public void testCreateWaitingRoom() {
        long id = waitingRoomManager.createWaitingRoom(user, password, bayonet, gameMode);
        assertEquals(0, id);

        verify(waitingRooms).createWaitingRoom(userCaptor.capture(), passwordCaptor.capture(), bayonetCaptor.capture(), gameModeCaptor.capture());
        assertEquals(user, userCaptor.getValue());
        assertEquals(password, passwordCaptor.getValue());
        assertEquals(Integer.valueOf(bayonet), bayonetCaptor.getValue());
        assertEquals(gameMode, gameModeCaptor.getValue());
    }

    @Test
    public void testAddUserReturnsTrue() {
        when(waitingRooms.addUser(user, 1, "password")).thenReturn(true);
        when(waitingRooms.addUser(user, 2, "password")).thenReturn(false);

        boolean added1 = waitingRoomManager.addUser(user, 1, "password");
        boolean added2 = waitingRoomManager.addUser(user, 2, "password");
        verify(waitingRooms, times(1)).addUser(user, 1, "password");
        verify(waitingRooms, times(1)).addUser(user, 2, "password");

        assertTrue(added1);
        assertFalse(added2);
    }

    @Test
    public void testGetAllRooms() {
        when(waitingRooms.getAllRooms()).thenReturn(new ArrayList<>());

        List<Room> result = waitingRoomManager.getAllRooms();
        verify(waitingRooms, times(1)).getAllRooms();

        assertEquals(0, result.size());
    }

    @Test
    public void testIsRoomReady() {
        when(waitingRooms.isRoomReady(1)).thenReturn(true);
        when(waitingRooms.isRoomReady(2)).thenReturn(false);

        boolean ready1 = waitingRoomManager.isRoomReady(1);
        boolean ready2 = waitingRoomManager.isRoomReady(2);

        verify(waitingRooms, times(1)).isRoomReady(1);
        verify(waitingRooms, times(1)).isRoomReady(2);
        verify(waitingRooms, times(2)).isRoomReady(anyLong());

        assertTrue(ready1);
        assertFalse(ready2);
    }

    @Test
    public void testGetVersion() {
        int version = 2;
        when(waitingRooms.getVersion()).thenReturn(version);

        assertEquals(version, waitingRoomManager.getVersion());
    }

    @Test
    public void testGetReadyRoom() {
        Room room = new Room();
        room.setId(1);
        room.setPassword("pass");
        room.setBayonet(2);
        room.setGameMode(GameMode.NINES);
        room.setPlayers(new ArrayList<>());

        when(waitingRooms.getReadyRoom(1)).thenReturn(room);
        Room result = waitingRoomManager.getReadyRoom(1);

        assertEquals(room.getId(), result.getId());
        assertEquals(room.getPassword(), result.getPassword());
        assertEquals(room.getBayonet(), result.getBayonet());
        assertEquals(room.getGameMode(), result.getGameMode());
    }

    @Test
    public void testRemoveRoom() {
        long roomId = 3;
        waitingRoomManager.removeRoom(roomId);

        verify(waitingRooms, times(1)).removeRoom(roomIdCaptor.capture());

        assertEquals(roomId, roomIdCaptor.getValue().longValue());
    }

    @Test
    public void testRemoveUserReturnsFalse() {
        User user1 = new User("name1", "mail1", "pass1");
        User user2 = new User("name2", "mail2", "pass2");

        when(waitingRooms.removeUser(user1, 1)).thenReturn(false);
        when(waitingRooms.removeUser(user2, 2)).thenReturn(true);

        boolean result1 = waitingRoomManager.removeUser(user1, 1);
        boolean result2 = waitingRoomManager.removeUser(user2, 2);

        verify(waitingRooms, times(1)).removeUser(user1, 1);
        verify(waitingRooms, times(1)).removeUser(user2, 2);

        assertFalse(result1);
        assertTrue(result2);
    }
}
