package com.joker.services.game;

import com.joker.game.*;
import com.joker.model.Room;
import com.joker.model.dto.TableResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GameServiceBean implements GameService {

    private static final Logger log = LoggerFactory.getLogger(GameServiceBean.class);

    /* TODO: need inMemory manager (with tableId accessor) */

    private Table table;

    @Override
    public void createTable(Room room) {
        switch (room.getGameMode()){
            case NINES:
                table = new GameNines(room.getPlayers(), room.getBayonet());
            case STANDARD:
            default:
                log.error("Not implemented Yet");
        }
    }

    @Override
    public TableResponse getTable(long tableId, long userId) {
        return table.getTable(userId);
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
