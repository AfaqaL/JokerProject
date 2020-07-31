package com.joker.services.history;

import com.joker.model.TableHistory;
import com.joker.model.dto.HistoryResponse;

import java.util.List;

public interface HistoryService {

    List<HistoryResponse> getUserHistory(long id);

    boolean addHistory(TableHistory history);
}
