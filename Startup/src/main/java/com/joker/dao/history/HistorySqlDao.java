package com.joker.dao.history;

import com.joker.model.TableHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository("historyDao")
public class HistorySqlDao implements HistoryDao {

    @Autowired
    private DataSource db;

    @Override
    public List<TableHistory> getUserHistory(long id) {
        try {
            PreparedStatement query = db.getConnection().prepareStatement(
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
            return res;
        } catch (SQLException e) {
            rollback();
            return null;
        }
    }

    @Override
    public boolean addHistory(TableHistory history) {
        try {
            PreparedStatement insert = db.getConnection().prepareStatement(
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
            return true;
        } catch (SQLException e) {
            rollback();
            return false;
        }
    }

    private void commit() throws SQLException {
        Statement stm = db.getConnection().createStatement();
        stm.executeQuery("COMMIT");
    }

    private void rollback() {
        try {
            Statement stm = db.getConnection().createStatement();
            stm.executeQuery("ROLLBACK");
        } catch (SQLException ignored) {

        }
    }
}
