package managers;

import com.joker.dao.InMemoryWaitingRoomDao;
import com.joker.managers.WaitingRoomManagerBean;
import com.joker.model.GameMode;
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
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WaitingRoomManagerTest {

    @InjectMocks
    private WaitingRoomManagerBean waitingRoomManager;

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
        waitingRoomManager.createWaitingRoom(user, password, bayonet, gameMode);

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
    public void testIsRoomReadyReturnsTrue() {
        when(waitingRooms.isRoomReady(1)).thenReturn(true);
        when(waitingRooms.isRoomReady(2)).thenReturn(false);

        boolean ready1 = waitingRoomManager.isRoomReady(1);
        boolean ready2 = waitingRoomManager.isRoomReady(2);

        verify(waitingRooms, times(1)).isRoomReady(1);
        verify(waitingRooms, times(1)).isRoomReady(2);
        verify(waitingRooms, times(2)).isRoomReady(any(long.class));

        assertTrue(ready1);
        assertFalse(ready2);
    }

    @Test
    public void testGetVersion() {
        assertEquals(0, waitingRoomManager.getVersion());

        waitingRoomManager.createWaitingRoom(user, password, bayonet, gameMode);
        assertEquals(1, waitingRoomManager.getVersion());

        when(waitingRooms.addUser(any(User.class), any(long.class), any(String.class))).thenReturn(false);
        waitingRoomManager.addUser(user, 1, "pass");
        assertEquals(1, waitingRoomManager.getVersion());

        when(waitingRooms.addUser(any(User.class), any(long.class), any(String.class))).thenReturn(true);
        waitingRoomManager.addUser(user, 1, "pass");
        assertEquals(2, waitingRoomManager.getVersion());
    }

    @Test
    public void testRemoveRoom() {
        Room room = new Room();
        room.setId(1);
        room.setPassword("pass");
        room.setBayonet(2);
        room.setGameMode(GameMode.NINES);
        room.setPlayers(new ArrayList<>());

        when(waitingRooms.removeRoom(1)).thenReturn(room);
        Room result = waitingRoomManager.removeRoom(1);

        assertEquals(room.getId(), result.getId());
        assertEquals(room.getPassword(), result.getPassword());
        assertEquals(room.getBayonet(), result.getBayonet());
        assertEquals(room.getGameMode(), result.getGameMode());
    }
}
