package com.joker.services.game;

import com.joker.game.Card;
import com.joker.model.Room;
import com.joker.model.dto.TableResponse;

public interface GameService {

    void createTable(Room room);

    TableResponse getTable(long tableId, long userId);

    int getVersion(long tableId);

    void declareNumber(long tableId, int num);

    void putCard(long tableId, Card card);

    void setSuperiorCard(long tableId, Card card);
}
