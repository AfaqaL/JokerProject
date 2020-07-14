package com.joker.dao;

import com.joker.model.TableHistory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistorySqlDao implements HistoryDao {

    private static HistorySqlDao instance;

    private final Connection connection;

    private HistorySqlDao(Connection connection) {
        this.connection = connection;
    }

    public static HistorySqlDao getInstance(Connection connection) {
        if (instance == null) {
            instance = new HistorySqlDao(connection);
        }
        return instance;
    }

    @Override
    public List<TableHistory> getUserHistory(long id) {
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
            return res;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public boolean addHistory(TableHistory history) {
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
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
