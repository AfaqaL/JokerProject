package com.joker.services.history;

import com.joker.model.TableHistory;

import java.util.List;

public interface HistoryService {

    List<TableHistory> getUserHistory(long id);

    boolean addHistory(TableHistory history);

    String getUsername(long id);
}
