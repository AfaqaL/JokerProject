package com.joker.services.game;

import com.joker.model.Room;
import com.joker.model.dto.CardDTO;
import com.joker.model.dto.TableResponse;

public interface GameService {

    void createTable(Room room);

    int getIndex(long tableId, long userId);

    TableResponse getTable(long tableId, long userId);

    int getVersion(long tableId);

    void declareNumber(long tableId, int num);

    void putCard(long tableId, CardDTO card);

    void setSuperiorCard(long tableId, CardDTO card);
}
