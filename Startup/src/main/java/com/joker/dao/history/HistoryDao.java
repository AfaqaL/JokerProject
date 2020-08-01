package com.joker.dao.history;

import com.joker.model.TableHistory;

import java.util.List;

public interface HistoryDao {

    /**
     * @param id - id of the user
     * @return list of tables where the
     * user played
     */
    List<TableHistory> getUserHistory(long id);

    /**
     * Adds new history in the database.
     *
     * @param history - struct to save information
     *                about table
     * @return true if user is added
     * in the database, false - otherwise
     */
    boolean addHistory(TableHistory history);

    /**
     * Checks if history with the id
     * exists in the database.
     *
     * @param tableId - id of table
     * @return true if history exists,
     * false - otherwise
     */
    boolean historyExists(long tableId);
}
