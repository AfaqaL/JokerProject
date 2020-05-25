package Databases;

import HelperClasses.TableHistory;

import java.util.List;

public interface HistoryDao {
    boolean createTable();

    List<TableHistory> getUserHistory(int id);

    boolean addHistory(TableHistory history);
}
