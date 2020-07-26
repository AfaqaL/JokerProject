package com.joker.services.game;

import com.joker.game.Card;
import com.joker.game.Table;
import com.joker.game.TableNines;
import com.joker.model.Room;
import com.joker.model.dto.TableResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GameServiceBean implements GameService {

    private static final Logger log = LoggerFactory.getLogger(GameServiceBean.class);

    private Table table;
    @Override
    public void createTable(Room room) {
        long[] playerIds = new long[Table.NUM_PLAYERS];
        for (int i = 0; i < Table.NUM_PLAYERS; i++) {
            playerIds[i] = room.getPlayers().get(i).getId();
        }
        switch (room.getGameMode()){
            case NINES:
                table = new TableNines(playerIds[0], playerIds[1], playerIds[2],playerIds[3]);
            case STANDARD:
            default:
                log.error("Not implemented yet");
        }
    }

    @Override
    public TableResponse getTable(long tableId, long userId) {
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
