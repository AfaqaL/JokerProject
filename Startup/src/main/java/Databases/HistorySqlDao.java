package Databases;

import Model.TableHistory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistorySqlDao implements HistoryDao {
    private Connection connection;

    public HistorySqlDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createTable() throws SQLException {
        Statement stm = connection.createStatement();
        stm.execute("CREATE TABLE IF NOT EXISTS histories (\n" +
                "    table_id BIGINT NOT NULL AUTO_INCREMENT,\n" +
                "    user_id1 BIGINT NOT NULL,\n" +
                "    score1 DOUBLE NOT NULL,\n" +
                "    user_id2 BIGINT NOT NULL,\n" +
                "    score2 DOUBLE NOT NULL,\n" +
                "    user_id3 BIGINT NOT NULL,\n" +
                "    score3 DOUBLE NOT NULL,\n" +
                "    user_id4 BIGINT NOT NULL,\n" +
                "    score4 DOUBLE NOT NULL,\n" +
                "    PRIMARY KEY (table_id),\n" +
                "    FOREIGN KEY (user_id1) REFERENCES users(user_id),\n" +
                "    FOREIGN KEY (user_id2) REFERENCES users(user_id),\n" +
                "    FOREIGN KEY (user_id3) REFERENCES users(user_id),\n" +
                "    FOREIGN KEY (user_id4) REFERENCES users(user_id)\n" +
                ");");
    }

    @Override
    public void dropTable() throws SQLException {
        Statement stm = connection.createStatement();
        stm.execute("DROP TABLE IF EXISTS histories;");
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
