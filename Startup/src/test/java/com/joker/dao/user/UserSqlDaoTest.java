package com.joker.dao.user;

import com.joker.model.User;
import dao.DatabaseInfo;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
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

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
class UserSqlDaoTest {


    @InjectMocks
    private UserSqlDao userDao;

    @Mock
    private DataSource db;

    private static Connection connection;


    @BeforeClass
    public static void setUp() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        connection = DriverManager.getConnection(
                DatabaseInfo.URL,
                DatabaseInfo.USERNAME,
                DatabaseInfo.PASSWORD
        );

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
                "    `rank` INT NOT NULL,\n" +
                "    PRIMARY KEY (user_id)\n" +
                ");");


        stm.execute("INSERT INTO users ( username, mail, password, `rank`) \n" +
                "VALUES ('user1', 'mail1', 'pass1', 10)");
        stm.execute("INSERT INTO users ( username, mail, password, `rank`) \n" +
                "VALUES ('user2', 'mail2', 'pass2', 20)");
        stm.execute("INSERT INTO users ( username, mail, password, `rank`) \n" +
                "VALUES ( 'user3', 'mail3', 'pass3', 30)");
        stm.execute("INSERT INTO users ( username, mail, password, `rank`) \n" +
                "VALUES ( 'user4', 'mail4', 'pass4', 40)");
        stm.execute("INSERT INTO users ( username, mail, password, `rank`) \n" +
                "VALUES ( 'user5', 'mail5', 'pass5', 50)");


    }


    @After
    public void dropTables() throws SQLException {
        Statement stm = connection.createStatement();
        stm.execute("DROP TABLE IF EXISTS histories");
        stm.execute("DROP TABLE IF EXISTS users");
    }


    @AfterClass
    public static void tearDown() throws SQLException {
        connection.close();
    }


    @Test
    void searchById() throws SQLException {
        User player1 = userDao.searchById(1);
        assertEquals("user1", player1.getUsername());
        User noPlayer = userDao.searchById(40);
        assertEquals(null, noPlayer);
        User Player2 = userDao.searchById(2);
        assertEquals("pass2", userDao.searchById(2).getPassword());


    }


    @Test
    void searchByMail() {
        User player1 = userDao.searchByMail("mail1");
        assertEquals("user1", player1.getUsername());

        assertEquals("user3", userDao.searchByMail("mail3").getUsername());

        assertEquals(null, userDao.searchByMail("blable"));
    }

    @Test
    void searchByUsername() {
        assertEquals("pas1", userDao.searchByUsername("user1").getPassword());
        assertEquals(50, userDao.searchByUsername("user5").getRank());
    }

    @Test
    void searchByUsernameAndPassword() {
        assertEquals(40, userDao.searchByUsernameAndPassword("user4", "pas4").getRank());
        assertEquals("mail3", userDao.searchByUsernameAndPassword("user3", "pas3").getMail());
        assertEquals(null, userDao.searchByUsernameAndPassword("user4", "pas3"));
        assertEquals(null, userDao.searchByUsernameAndPassword("ew", "pas3"));
        assertEquals(null, userDao.searchByUsernameAndPassword("", ""));
    }

    @Test
    void searchByUsernameAndMail() {
        assertEquals(30, userDao.searchByUsernameAndMail("user3", "mail3").getRank());
        assertEquals("pass5", userDao.searchByUsernameAndMail("user5", "mail5"));
        assertEquals(null, userDao.searchByUsernameAndMail("user2", "pass2"));
    }

    @Test
    void addUser() {
        User user12 = new User("user12", "mail12", "pass12");
        User user13 = new User(19, "user13", "mail13", "pas13", 120);
        userDao.addUser(user12);
        userDao.addUser(user12);
        assertEquals("pas12", userDao.searchByUsername("user12"));
        assertEquals("user13", userDao.searchById(19).getUsername());
    }

    @Test
    void changePassword() {
        User user1 = userDao.searchById(1);
        userDao.changePassword(user1, "newPass");
        assertEquals("newPass", user1.getPassword());
    }

    @Test
    void changeRank() {
        User user1 = userDao.searchById(1);
        userDao.changeRank(user1, 135);
        assertEquals(135, user1.getRank());
    }
}