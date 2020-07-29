package managers;

import com.joker.dao.user.UserSqlDao;
import com.joker.model.User;
import com.joker.services.user.UserServiceBean;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceBean userService;

    @Mock
    private UserSqlDao userDao;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @Captor
    private ArgumentCaptor<String> passwordCaptor;

    @Captor
    private ArgumentCaptor<Integer> rankCaptor;

    private static User user;

    @BeforeClass
    public static void setUp() {
        user = new User(1, "user", "mail", "pass", 10);
    }

    @Test
    public void testGetUsernameThrowsException() {
        when(userDao.searchById(anyLong())).thenReturn(null);
        assertThrows(NullPointerException.class, () -> userService.getUsername(anyLong()));
    }

    @Test
    public void testGetUsername() {
        when(userDao.searchById(anyLong())).thenReturn(user);

        assertEquals(user.getUsername(), userService.getUsername(anyLong()));
    }

    @Test
    public void testGetByUsernameAndPassword() {
        when(userDao.searchByUsernameAndPassword(anyString(), anyString())).thenReturn(null);
        assertNull(userService.getByUsernameAndPassword(anyString(), anyString()));

        when(userDao.searchByUsernameAndPassword(anyString(), anyString())).thenReturn(user);
        assertSame(user, userService.getByUsernameAndPassword(anyString(), anyString()));
    }

    @Test
    public void testGetByUsernameAndMail() {
        when(userDao.searchByUsernameAndMail(anyString(), anyString())).thenReturn(null);
        assertNull(userService.getByUsernameAndMail(anyString(), anyString()));

        when(userDao.searchByUsernameAndMail(anyString(), anyString())).thenReturn(user);
        assertSame(user, userService.getByUsernameAndMail(anyString(), anyString()));
    }

    @Test
    public void testGetByMail() {
        when(userDao.searchByMail(anyString())).thenReturn(null);
        assertNull(userService.getByMail(anyString()));

        when(userDao.searchByMail(anyString())).thenReturn(user);
        assertSame(user, userService.getByMail(anyString()));
    }

    @Test
    public void testAddUser() {
        userService.addUser(user);

        verify(userDao, times(1)).addUser(userCaptor.capture());

        assertEquals(user, userCaptor.getValue());
    }

    @Test
    public void testChangePassword() {
        String password = "pass";
        userService.changePassword(user, password);

        verify(userDao, times(1)).changePassword(userCaptor.capture(), passwordCaptor.capture());

        assertEquals(user, userCaptor.getValue());
        assertEquals(password, passwordCaptor.getValue());
    }

    @Test
    public void testChangeRank() {
        int newRank = 20;
        userService.changeRank(user, newRank);

        verify(userDao, times(1)).changeRank(userCaptor.capture(), rankCaptor.capture());

        assertEquals(user, userCaptor.getValue());
        assertEquals(newRank, rankCaptor.getValue().intValue());
    }
}
