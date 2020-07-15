package com.joker.dao;

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
}
