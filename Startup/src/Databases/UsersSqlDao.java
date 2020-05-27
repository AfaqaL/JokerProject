package Databases;

import HelperClasses.User;

import java.sql.*;

public class UsersSqlDao implements UsersDao {
    private Connection connection;

    public UsersSqlDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createTable() throws SQLException {
        Statement stm = connection.createStatement();
        stm.execute("CREATE TABLE IF NOT EXISTS users (\n" +
                "    user_id BIGINT NOT NULL AUTO_INCREMENT,\n" +
                "    username VARCHAR(20) NOT NULL UNIQUE,\n" +
                "    mail VARCHAR(32) NOT NULL UNIQUE,\n" +
                "    password VARCHAR(20) NOT NULL,\n" +
                "    rank INT NOT NULL,\n" +
                "    PRIMARY KEY (user_id)\n" +
                ");");
    }

    @Override
    public void dropTable() throws SQLException {
        Statement stm = connection.createStatement();
        stm.execute("DROP TABLE IF EXISTS users;");
    }

    @Override
    public User searchById(long id) {
        try {
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

            return new User(id, username, mail, password, rank);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public User searchByMail(String mail) {
        try {
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

            return new User(id, username, mail, password, rank);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public User searchByUsername(String username) {
        try {
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

            return new User(id, username, mail, password, rank);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public User searchByUsernameAndPassword(String username, String password) {
        try {
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

            return new User(id, username, mail, password, rank);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public boolean addUser(String username, String password, String mail) {
        try {
            if (searchByUsername(username) != null || searchByMail(mail) != null) {
                return false;
            }

            PreparedStatement insert = connection.prepareStatement(
                    "INSERT INTO users (username, mail, password, rank)\n" +
                            "VALUES (?, ?, ?, 0);"
            );
            insert.setString(1, username);
            insert.setString(2, mail);
            insert.setString(3, password);

            insert.execute();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean changePassword(String username, String newPassword) {
        try {
            PreparedStatement update = connection.prepareStatement(
                    "UPDATE users SET password = ?\n" +
                            "WHERE username = ?;"
            );
            update.setString(1, newPassword);
            update.setString(2, username);

            update.execute();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean changeRank(String username, int newRank) {
        try {
            PreparedStatement update = connection.prepareStatement(
                    "UPDATE users SET rank = ?\n" +
                            "WHERE username = ?;"
            );
            update.setLong(1, newRank);
            update.setString(2, username);

            update.execute();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
