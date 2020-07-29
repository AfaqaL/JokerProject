package com.joker.dao.history;

import com.joker.model.TableHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.transaction.Transactional;
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
        try {
            Connection connection = db.getConnection();
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

            List<TableHistory> res = new ArrayList<>();
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

            commit();

            connection.close();
            return res;
        } catch (SQLException e) {
            rollback();
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean addHistory(TableHistory history) {
        try {
            Connection connection = db.getConnection();
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
