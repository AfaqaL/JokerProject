package com.joker.dao.history;

import com.joker.model.TableHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository("historyDao")
public class HistorySqlDao implements HistoryDao {

    private static final Logger log = LoggerFactory.getLogger(HistorySqlDao.class);

    @Autowired
    private DataSource db;

    @Override
    public List<TableHistory> getUserHistory(long id) {
        Connection connection = getConnection();
        List<TableHistory> res = new ArrayList<>();
        if (connection == null) {
            return res;
        }

        try {
            PreparedStatement query = connection.prepareStatement(
                    "SELECT * FROM histories\n" +
                            "WHERE user_id1 = ? OR\n" +
                            "    user_id2 = ? OR\n" +
                            "    user_id3 = ? OR\n" +
                            "    user_id4 = ?;"
            );
            for (int i = 1; i <= 4; i++) {
                query.setLong(i, id);
            }

            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                TableHistory history = new TableHistory(
                        rs.getLong(1),
                        rs.getLong(2), rs.getDouble(3),
                        rs.getLong(4), rs.getDouble(5),
                        rs.getLong(6), rs.getDouble(7),
                        rs.getLong(8), rs.getDouble(9)
                );
                res.add(history);
            }

            rs.close();
            query.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                log.error(ex.getMessage());
            }
        }
        return res;
    }

    @Override
    public boolean addHistory(TableHistory history) {
        Connection connection = getConnection();
        if (connection == null) {
            return false;
        }

        try {
            PreparedStatement insert = connection.prepareStatement(
                    "INSERT INTO histories \n" +
                            "VALUES (?, ?, ?, ?, ?,\n" +
                            "        ?, ?, ?, ?);"
            );
            insert.setLong(1, history.getTableId());
            insert.setLong(2, history.getId1());
            insert.setDouble(3, history.getScore1());
            insert.setLong(4, history.getId2());
            insert.setDouble(5, history.getScore2());
            insert.setLong(6, history.getId3());
            insert.setDouble(7, history.getScore3());
            insert.setLong(8, history.getId4());
            insert.setDouble(9, history.getScore4());

            insert.execute();

            insert.close();
            commit(connection);

            return true;
        } catch (SQLException ex) {
            rollback(connection);
            log.error(ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean historyExists(long tableId) {
        Connection connection = getConnection();
        if (connection == null) {
            return false;
        }

        boolean exists = false;

        try {
            PreparedStatement query = connection.prepareStatement(
                    "SELECT table_id FROM histories WHERE table_id = ?;"
            );
            query.setLong(1, tableId);

            ResultSet rs = query.executeQuery();
            exists = rs.next();

            rs.close();
            query.close();
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                log.error(ex.getMessage());
            }
        }

        return exists;
    }

    private Connection getConnection() {
        try {
            return db.getConnection();
        } catch (SQLException ex) {
            log.info("Error occurred while trying to get connection from DataSource");
            return null;
        }
    }

    private void commit(Connection connection) throws SQLException {
        connection.commit();
        connection.close();
    }

    private void rollback(Connection connection) {
        try {
            connection.rollback();
            connection.close();
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
    }
}
