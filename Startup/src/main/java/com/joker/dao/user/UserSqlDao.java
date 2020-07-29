package com.joker.dao.user;

import com.joker.model.User;
import com.joker.services.game.GameServiceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.sql.*;

@Repository("userDao")
public class UserSqlDao implements UserDao {


    private static final Logger log = LoggerFactory.getLogger(UserSqlDao.class);
    @Autowired
    private DataSource db;

    @Override
    public User searchById(long id) {
        try {
            Connection connection = db.getConnection();
            PreparedStatement query = connection.prepareStatement(
                    "SELECT * FROM users WHERE user_id = ?;"
            );
            query.setLong(1, id);

            ResultSet rs = query.executeQuery();
            if (!rs.next()) return null;

            String username = rs.getString(2);
            String mail = rs.getString(3);
            String password = rs.getString(4);
            int rank = rs.getInt(5);

            commit();
            connection.close();
            return new User(id, username, mail, password, rank);
        } catch (SQLException e) {
            rollback();
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public User searchByMail(String mail) {
        try {
            Connection connection= db.getConnection();
            PreparedStatement query = connection.prepareStatement(
                    "SELECT * FROM users WHERE mail = ?;"
            );
            query.setString(1, mail);

            ResultSet rs = query.executeQuery();
            if (!rs.next()) return null;

            long id = rs.getLong(1);
            String username = rs.getString(2);
            String password = rs.getString(4);
            int rank = rs.getInt(5);

            commit();
            connection.close();
            return new User(id, username, mail, password, rank);
        } catch (SQLException e) {
            rollback();
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public User searchByUsername(String username) {
        try {
            Connection connection = db.getConnection();
            PreparedStatement query = connection.prepareStatement(
                    "SELECT * FROM users WHERE username = ?;"
            );
            query.setString(1, username);

            ResultSet rs = query.executeQuery();
            if (!rs.next()) return null;

            long id = rs.getLong(1);
            String mail = rs.getString(3);
            String password = rs.getString(4);
            int rank = rs.getInt(5);

            commit();
            connection.close();
            return new User(id, username, mail, password, rank);
        } catch (SQLException e) {
            rollback();
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public User searchByUsernameAndPassword(String username, String password) {
        try {
            Connection connection= db.getConnection();
            PreparedStatement query = connection.prepareStatement(
                    "SELECT * FROM users\n" +
                            "WHERE username = ? AND\n" +
                            "    password = ?;"
            );
            query.setString(1, username);
            query.setString(2, password);

            ResultSet rs = query.executeQuery();
            if (!rs.next()) return null;

            long id = rs.getLong(1);
            String mail = rs.getString(3);
            int rank = rs.getInt(5);

            commit();
            connection.close();
            return new User(id, username, mail, password, rank);
        } catch (SQLException e) {
            rollback();
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public User searchByUsernameAndMail(String username, String mail) {
        try {
            Connection connection = db.getConnection();
            PreparedStatement query = connection.prepareStatement(
                    "SELECT * FROM users\n" +
                            "WHERE username = ? OR\n" +
                            "    mail = ?;"
            );
            query.setString(1, username);
            query.setString(2, mail);

            ResultSet rs = query.executeQuery();
            if (!rs.next()) return null;

            long id = rs.getLong(1);
            String password = rs.getString(3);
            int rank = rs.getInt(5);

            commit();
            connection.close();
            return new User(id, username, mail, password, rank);
        } catch (SQLException e) {
            rollback();
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean addUser(User user) {
        String username = user.getUsername();
        String mail = user.getMail();
        String password = user.getPassword();
        try {
            if (searchByUsernameAndMail(username, mail) != null) {
                return false;
            }

            Connection connection = db.getConnection();
            PreparedStatement insert = connection.prepareStatement(
                    "INSERT INTO users (username, mail, password, `rank`)\n" +
                            "VALUES (?, ?, ?, 0);"
            );
            insert.setString(1, username);
            insert.setString(2, mail);
            insert.setString(3, password);

            insert.execute();

            commit();
            connection.close();
            return true;
        } catch (SQLException e) {
            rollback();
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean changePassword(User user, String newPassword) {
        try {
            Connection connection = db.getConnection();
            PreparedStatement update = connection.prepareStatement(
                    "UPDATE users SET password = ?\n" +
                            "WHERE username = ?;"
            );
            update.setString(1, newPassword);
            update.setString(2, user.getUsername());

            update.execute();

            commit();
            connection.close();
            return true;
        } catch (SQLException e) {
            rollback();
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean changeRank(User user, int newRank) {
        try {
            Connection connection = db.getConnection();
            PreparedStatement update = connection.prepareStatement(
                    "UPDATE users SET `rank` = ?\n" +
                            "WHERE username = ?;"
            );
            update.setLong(1, newRank);
            update.setString(2, user.getUsername());

            update.execute();

            commit();
            connection.close();
            return true;
        } catch (SQLException e) {
            rollback();
            log.error(e.getMessage());
            return false;
        }
    }

    private void commit() throws SQLException {
        Connection connection = db.getConnection();
        Statement stm = connection.createStatement();
        stm.executeQuery("COMMIT");
        connection.close();
    }

    private void rollback() {
        try {
            Connection connection = db.getConnection();
            Statement stm = connection.createStatement();
            stm.executeQuery("ROLLBACK");
            connection.close();
        } catch (SQLException ignored) {
            log.error(ignored.getMessage());
        }
    }
}
