package com.joker.services.game;

import com.joker.game.Card;
import com.joker.model.Room;
import com.joker.model.Table;

public interface GameService {

    void createTable(Room room);

    Table getTable(long tableId, long userId);

    int getVersion(long tableId);

    void declareNumber(long tableId, int num);

    void putCard(long tableId, Card card);

    void setSuperiorCard(long tableId, Card card);
}
