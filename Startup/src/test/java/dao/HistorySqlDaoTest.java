package dao;

import com.joker.dao.history.HistorySqlDao;
import com.joker.model.TableHistory;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class HistorySqlDaoTest {

    @InjectMocks
    private HistorySqlDao historyDao;

    @Mock
    private DataSource db;

    private static Connection connection;

    private static final TableHistory history1 = new TableHistory();

    private static final TableHistory history2 = new TableHistory();

    private static final List<TableHistory> histories = new ArrayList<>();

    @BeforeClass
    public static void setUp() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        connection = DriverManager.getConnection(
                DatabaseInfo.URL,
                DatabaseInfo.USERNAME,
                DatabaseInfo.PASSWORD
        );

        history1.setTableId(1);
        history1.setId1(1);
        history1.setScore1(2.5);
        history1.setId2(2);
        history1.setScore2(1.2);
        history1.setId3(3);
        history1.setScore3(7.3);
        history1.setId4(4);
        history1.setScore4(4.5);

        history2.setTableId(2);
        history2.setId1(2);
        history2.setScore1(3.3);
        history2.setId2(4);
        history2.setScore2(8.2);
        history2.setId3(1);
        history2.setScore3(4.2);
        history2.setId4(5);
        history2.setScore4(7.0);

        histories.add(history1);
        histories.add(history2);
    }

    @Before
    public void createNewTables() throws SQLException {
        Mockito.when(db.getConnection()).thenReturn(connection);
        Statement stm = connection.createStatement();

        stm.execute("CREATE TABLE IF NOT EXISTS users (\n" +
                "    user_id BIGINT NOT NULL AUTO_INCREMENT,\n" +
                "    username VARCHAR(20) NOT NULL UNIQUE,\n" +
                "    mail VARCHAR(32) NOT NULL UNIQUE,\n" +
                "    password VARCHAR(20) NOT NULL,\n" +
                "    rank INT NOT NULL,\n" +
                "    PRIMARY KEY (user_id)\n" +
                ");");

        stm.execute("CREATE TABLE IF NOT EXISTS histories (\n" +
                "    table_id BIGINT NOT NULL,\n" +
                "    user_id1 BIGINT NOT NULL,\n" +
                "    score1   DOUBLE NOT NULL,\n" +
                "    user_id2 BIGINT NOT NULL,\n" +
                "    score2   DOUBLE NOT NULL,\n" +
                "    user_id3 BIGINT NOT NULL,\n" +
                "    score3   DOUBLE NOT NULL,\n" +
                "    user_id4 BIGINT NOT NULL,\n" +
                "    score4   DOUBLE NOT NULL,\n" +
                "    PRIMARY KEY (table_id),\n" +
                "    FOREIGN KEY (user_id1) REFERENCES users (user_id),\n" +
                "    FOREIGN KEY (user_id2) REFERENCES users (user_id),\n" +
                "    FOREIGN KEY (user_id3) REFERENCES users (user_id),\n" +
                "    FOREIGN KEY (user_id4) REFERENCES users (user_id)\n" +
                ");");

        stm.execute("INSERT INTO users (username, mail, password, `rank`) \n" +
                "VALUES ('user1', 'mail1', 'pass1', 10)");
        stm.execute("INSERT INTO users (username, mail, password, `rank`) \n" +
                "VALUES ('user2', 'mail2', 'pass2', 20)");
        stm.execute("INSERT INTO users (username, mail, password, `rank`) \n" +
                "VALUES ('user3', 'mail3', 'pass3', 30)");
        stm.execute("INSERT INTO users (username, mail, password, `rank`) \n" +
                "VALUES ('user4', 'mail4', 'pass4', 40)");
        stm.execute("INSERT INTO users (username, mail, password, `rank`) \n" +
                "VALUES ('user5', 'mail5', 'pass5', 50)");

        stm.execute("INSERT INTO histories (table_id, user_id1, score1, user_id2, score2, user_id3, score3, user_id4, score4)\n" +
                "VALUES (1, 1, 2.5, 2, 1.2, 3, 7.3, 4, 4.5)");

        stm.execute("INSERT INTO histories (table_id, user_id1, score1, user_id2, score2, user_id3, score3, user_id4, score4)\n" +
                "VALUES (2, 2, 3.3, 4, 8.2, 1, 4.2, 5, 7.0)");
    }

    @After
    public void dropTables() throws SQLException {
        Statement stm = connection.createStatement();
        stm.execute("DROP TABLE IF EXISTS histories");
        stm.execute("DROP TABLE IF EXISTS users");
    }

    @Test
    public void testGetUserHistory() {
        List<TableHistory> actual = historyDao.getUserHistory(1);
        assertNotNull(actual);
        assertArrayEquals(new List[]{histories}, new List[]{actual});

        actual = historyDao.getUserHistory(5);
        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertEquals(history2, actual.get(0));
    }

    @Test
    public void testAddHistory() {
        TableHistory history = new TableHistory();
        history.setTableId(3);
        history.setId1(2);
        history.setScore1(3.0);
        history.setId2(4);
        history.setScore2(8.0);
        history.setId3(3);
        history.setScore3(4.0);
        history.setId4(5);
        history.setScore4(7.0);

        assertTrue(historyDao.addHistory(history));

        List<TableHistory> actual = historyDao.getUserHistory(1);
        assertNotNull(actual);
        assertArrayEquals(new List[]{histories}, new List[]{actual});

        histories.clear();
        histories.add(history2);
        histories.add(history);

        actual = historyDao.getUserHistory(5);
        assertNotNull(actual);
        assertArrayEquals(new List[]{histories}, new List[]{actual});
    }

    @Test
    public void failGetUserHistory() throws SQLException {
        Statement stm = connection.createStatement();
        stm.execute("DROP TABLE IF EXISTS histories");

        List<TableHistory> actual = historyDao.getUserHistory(1);
        assertNull(actual);
    }

    @Test
    public void failAddUserHistory() throws SQLException {
        Statement stm = connection.createStatement();
        stm.execute("DROP TABLE IF EXISTS histories");

        assertFalse(historyDao.addHistory(history1));
    }
}
