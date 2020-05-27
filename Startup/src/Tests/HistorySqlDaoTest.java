package Tests;

import Databases.HistorySqlDao;
import Databases.UsersSqlDao;
import HelperClasses.TableHistory;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HistorySqlDaoTest {
    private HistorySqlDao dao;

    // There are foreign keys in the histories table,
    // that references on the users table, so we need
    // to create users table for histories table.
    private UsersSqlDao usersDao;

    @BeforeAll
    public void setUp() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost",
                "root",
                "root"
        );

        dao = new HistorySqlDao(connection);
        usersDao = new UsersSqlDao(connection);
        Statement stm = connection.createStatement();
        stm.execute("USE joker;");

        // Create users table and add users that will be used
        // in the tests.
        usersDao.createTable();
        for (int i = 0; i < 10; i++) {
            usersDao.addUser("user" + i, "pass", "mail" + i);
        }
    }

    @AfterAll
    public void tearDown() throws SQLException {
        usersDao.dropTable();
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
    public void zeroHistory() {
        assertEquals(0, dao.getUserHistory(1).size());
        assertEquals(0, dao.getUserHistory(100).size());
    }

    @Test
    public void oneHistory() {
        TableHistory history = new TableHistory(
                100100,
                1, 6.7,
                7, 12.3,
                9, 5.5,
                4, -2.2
        );


        assertTrue(dao.addHistory(history));
        assertEquals(0, dao.getUserHistory(2).size());

        List<TableHistory> expected = new ArrayList<>(Collections.singletonList(history));
        assertListEquals(expected, dao.getUserHistory(1));
        assertListEquals(expected, dao.getUserHistory(7));
        assertListEquals(expected, dao.getUserHistory(9));
        assertListEquals(expected, dao.getUserHistory(4));
    }

    @Test
    public void threeHistory() {
        TableHistory h1 = new TableHistory(
                100101,
                1, 6.7,
                2, 12.3,
                3, 5.5,
                4, -2.2
        );
        TableHistory h2 = new TableHistory(
                100102,
                1, 10.2,
                3, 20.5,
                5, 7.15,
                9, 3.4
        );
        TableHistory h3 = new TableHistory(
                100103,
                2, 7.11,
                8, 8.7,
                1, 0.5,
                10, 1.2
        );

        assertTrue(dao.addHistory(h1));
        assertFalse(dao.addHistory(h1));
        assertTrue(dao.addHistory(h2));
        assertFalse(dao.addHistory(h2));
        assertTrue(dao.addHistory(h3));
        assertFalse(dao.addHistory(h3));

        assertEquals(0, dao.getUserHistory(211).size());

        // Players who play only one game
        List<TableHistory> expected = new ArrayList<>(Collections.singletonList(h1));
        assertListEquals(expected, dao.getUserHistory(4));

        expected.clear();
        expected.addAll(Collections.singletonList(h2));
        assertListEquals(expected, dao.getUserHistory(5));
        assertListEquals(expected, dao.getUserHistory(9));

        expected.clear();
        expected.addAll(Collections.singletonList(h3));
        assertListEquals(expected, dao.getUserHistory(8));
        assertListEquals(expected, dao.getUserHistory(10));

        // Players who play two games
        expected.clear();
        expected.addAll(Arrays.asList(h1, h3));
        assertListEquals(expected, dao.getUserHistory(2));

        expected.clear();
        expected.addAll(Arrays.asList(h1, h2));
        assertListEquals(expected, dao.getUserHistory(3));

        // Player who play all games
        expected.clear();
        expected.addAll(Arrays.asList(h1, h2, h3));
        assertListEquals(expected, dao.getUserHistory(1));
    }

    @Test
    public void failMethods() {
        assertDoesNotThrow(() -> dao.dropTable());

        assertNull(dao.getUserHistory(1));
    }

    /**
     * Checks if two lists have same number of
     * elements and if they have same tableHistories
     * @param expected - list of tableHistories
     * @param result - list of tableHistories
     */
    private void assertListEquals(List<TableHistory> expected, List<TableHistory> result) {
        assertNotNull(result);
        assertEquals(expected.size(), result.size());

        for (TableHistory history : expected) {
            assertTrue(result.contains(history));
        }
    }
}