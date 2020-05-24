package Databases;

import HelperClasses.TableHistory;

import java.util.List;

public class HistorySqlDao implements HistoryDao {
    @Override
    public boolean createTable() {
        return false;
    }

    @Override
    public List<TableHistory> getUserHistory(int id) {
        return null;
    }

    @Override
    public boolean addHistory(TableHistory history) {
        return false;
    }
}
