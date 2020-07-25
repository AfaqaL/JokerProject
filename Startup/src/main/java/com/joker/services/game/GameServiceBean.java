package com.joker.services.game;

import com.joker.game.Card;
import com.joker.model.Room;
import com.joker.model.Table;
import org.springframework.stereotype.Service;

@Service
public class GameServiceBean implements GameService {

    @Override
    public void createTable(Room room) {

    }

    @Override
    public Table getTable(long tableId) {
        return null;
    }

    @Override
    public int getVersion(long tableId) {
        return 0;
    }

    @Override
    public void declareNumber(long tableId, int num) {

    }

    @Override
    public void putCard(long tableId, Card card) {

    }

    @Override
    public void setSuperiorCard(long tableId, Card card) {

    }
}
