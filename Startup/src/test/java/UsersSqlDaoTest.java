import com.joker.databases.UsersSqlDao;
import com.joker.model.User;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UsersSqlDaoTest {
    private UsersSqlDao dao;

    @BeforeAll
    public void setUp() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost",
                "root",
                "root"
        );

        dao = new UsersSqlDao(connection);
        Statement stm = connection.createStatement();
        stm.execute("USE joker;");
    }

    @BeforeEach
    public void create() {
        assertDoesNotThrow(() -> dao.createTable());
    }

    @AfterEach
    public void drop() {
        assertDoesNotThrow(() -> dao.dropTable());
    }

    @Test
    public void addUser() {
        assertTrue(dao.addUser("user1", "pass1", "test1@mail.com"));
        assertFalse(dao.addUser("user1", "pass2", "test2@mail.com"));
        assertFalse(dao.addUser("user2", "pass2", "test1@mail.com"));

        assertTrue(dao.addUser("user2", "pass1", "test2@mail.com"));
        assertFalse(dao.addUser("user2", "pass1", "test1@mail.com"));

        assertTrue(dao.addUser("user3", "pass2", "test3@mail.com"));

        assertEquals(1, dao.searchById(1).getId());
        assertEquals(2, dao.searchById(2).getId());
        assertEquals(3, dao.searchById(3).getId());
    }

    @Test
    public void searchById() {
        assertNull(dao.searchById(1));

        User user1 = new User(1, "user1", "test1@mail.com", "pass1", 0);
        assertTrue(dao.addUser("user1", "pass1", "test1@mail.com"));
        assertEquals(user1, dao.searchById(1));

        User user2 = new User(2, "user2", "test2@mail.com", "pass2", 0);
        assertTrue(dao.addUser("user2", "pass2", "test2@mail.com"));
        assertEquals(user2, dao.searchById(2));

        User user3 = new User(3, "user3", "test3@mail.com", "pass3", 0);
        assertTrue(dao.addUser("user3", "pass3", "test3@mail.com"));
        assertEquals(user3, dao.searchById(3));

        assertEquals(user1, dao.searchById(1));
        assertEquals(user2, dao.searchById(2));
        assertNull(dao.searchById(4));
    }

    @Test
    public void searchByMail() {
        assertNull(dao.searchByMail("not@exists"));

        User user1 = new User(1, "user1", "test1@mail.com", "pass1", 0);
        assertTrue(dao.addUser("user1", "pass1", "test1@mail.com"));
        assertEquals(user1, dao.searchByMail("test1@mail.com"));

        User user2 = new User(2, "user2", "test2@mail.com", "pass2", 0);
        assertTrue(dao.addUser("user2", "pass2", "test2@mail.com"));
        assertEquals(user2, dao.searchByMail("test2@mail.com"));

        User user3 = new User(3, "user3", "test3@mail.com", "pass3", 0);
        assertTrue(dao.addUser("user3", "pass3", "test3@mail.com"));
        assertEquals(user3, dao.searchByMail("test3@mail.com"));

        assertEquals(user1, dao.searchByMail("test1@mail.com"));
        assertEquals(user2, dao.searchByMail("test2@mail.com"));
        assertNull(dao.searchByMail("not@exists"));
    }

    @Test
    public void searchByUsername() {
        assertNull(dao.searchByUsername("not_exists"));

        User user1 = new User(1, "user1", "test1@mail.com", "pass1", 0);
        assertTrue(dao.addUser("user1", "pass1", "test1@mail.com"));
        assertEquals(user1, dao.searchByUsername("user1"));

        User user2 = new User(2, "user2", "test2@mail.com", "pass2", 0);
        assertTrue(dao.addUser("user2", "pass2", "test2@mail.com"));
        assertEquals(user2, dao.searchByUsername("user2"));

        User user3 = new User(3, "user3", "test3@mail.com", "pass3", 0);
        assertTrue(dao.addUser("user3", "pass3", "test3@mail.com"));
        assertEquals(user3, dao.searchByUsername("user3"));

        assertEquals(user1, dao.searchByUsername("user1"));
        assertEquals(user2, dao.searchByUsername("user2"));
        assertNull(dao.searchByUsername("not_exists"));
    }

    @Test
    public void searchByUsernameAndPassword() {
        assertNull(dao.searchByUsernameAndPassword("not_exists", "strong_password"));

        User user1 = new User(1, "user1", "test1@mail.com", "pass1", 0);
        assertTrue(dao.addUser("user1", "pass1", "test1@mail.com"));
        assertEquals(user1, dao.searchByUsernameAndPassword("user1", "pass1"));

        User user2 = new User(2, "user2", "test2@mail.com", "pass2", 0);
        assertTrue(dao.addUser("user2", "pass2", "test2@mail.com"));
        assertEquals(user2, dao.searchByUsernameAndPassword("user2", "pass2"));

        User user3 = new User(3, "user3", "test3@mail.com", "pass3", 0);
        assertTrue(dao.addUser("user3", "pass3", "test3@mail.com"));
        assertEquals(user3, dao.searchByUsernameAndPassword("user3", "pass3"));

        assertEquals(user1, dao.searchByUsernameAndPassword("user1", "pass1"));
        assertEquals(user2, dao.searchByUsernameAndPassword("user2", "pass2"));
        assertNull(dao.searchByUsernameAndPassword("not_exists", "very_strong_password"));
    }

    @Test
    public void changePassword() {
        assertTrue(dao.addUser("user1", "pass1", "test1@mail.com"));
        assertNotNull(dao.searchByUsernameAndPassword("user1", "pass1"));
        assertTrue(dao.changePassword("user1", "newPass"));

        User user1 = new User(1, "user1", "test1@mail.com", "newPass", 0);
        assertNull(dao.searchByUsernameAndPassword("user1", "pass1"));
        assertEquals(user1, dao.searchById(1));
        assertEquals(user1, dao.searchByUsernameAndPassword("user1", "newPass"));

        assertTrue(dao.addUser("user2", "pass2", "test2@mail.com"));
        assertNotNull(dao.searchByUsernameAndPassword("user2", "pass2"));
        assertTrue(dao.changePassword("user1", "pass1"));

        assertNull(dao.searchByUsernameAndPassword("user1", "newPass"));
        assertNotEquals(user1, dao.searchById(1));
        assertNotEquals(user1, dao.searchByUsernameAndPassword("user1", "pass1"));

        assertTrue(dao.changePassword("user2", "newPass"));
        User user2 = new User(2, "user2", "test2@mail.com", "newPass", 0);
        assertEquals(user2, dao.searchById(2));
        assertEquals(user2, dao.searchByUsernameAndPassword("user2", "newPass"));
        assertEquals(user2, dao.searchByUsername("user2"));
        assertEquals(user2, dao.searchByMail("test2@mail.com"));
    }

    @Test
    public void changeRank() {
        assertTrue(dao.addUser("user1", "pass1", "test1@mail.com"));
        assertTrue(dao.changeRank("user1", 50));

        User user1 = new User(1, "user1", "test1@mail.com", "pass1", 50);
        assertEquals(user1, dao.searchById(1));
        assertEquals(user1, dao.searchByUsernameAndPassword("user1", "pass1"));

        assertTrue(dao.addUser("user2", "pass2", "test2@mail.com"));
        assertTrue(dao.changeRank("user1", 75));

        user1 = new User(1, "user1", "test1@mail.com", "pass1", 75);
        assertEquals(user1, dao.searchById(1));
        assertEquals(user1, dao.searchByUsernameAndPassword("user1", "pass1"));

        assertTrue(dao.changeRank("user2", 250));
        User user2 = new User(2, "user2", "test2@mail.com", "pass2", 250);
        assertEquals(user2, dao.searchById(2));
        assertEquals(user2, dao.searchByUsernameAndPassword("user2", "pass2"));
        assertEquals(user2, dao.searchByUsername("user2"));
        assertEquals(user2, dao.searchByMail("test2@mail.com"));
    }

    @Test
    public void changePasswordAndRank() {
        assertTrue(dao.addUser("user1", "pass1", "test1@mail.com"));
        assertTrue(dao.changeRank("user1", 50));
        assertTrue(dao.changePassword("user1", "newPass"));

        User user1 = new User(1, "user1", "test1@mail.com", "newPass", 50);
        assertEquals(user1, dao.searchById(1));
        assertNull(dao.searchByUsernameAndPassword("user1", "pass1"));
        assertEquals(user1, dao.searchByUsernameAndPassword("user1", "newPass"));
        assertEquals(user1, dao.searchByUsername("user1"));
        assertEquals(user1, dao.searchByMail("test1@mail.com"));
    }

    @Test
    public void failMethods() {
        assertDoesNotThrow(() -> dao.dropTable());

        assertNull(dao.searchById(1));
        assertNull(dao.searchByMail("mail"));
        assertNull(dao.searchByUsername("name"));
        assertNull(dao.searchByUsernameAndPassword("name", "pass"));

        assertFalse(dao.addUser("name", "pass", "test@mail.com"));
        assertFalse(dao.changePassword("name", "pass"));
        assertFalse(dao.changeRank("user", 50));
    }
}