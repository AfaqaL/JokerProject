package com.joker.dao.table;

import com.joker.game.GameNines;
import com.joker.game.GameStandard;
import com.joker.game.Table;
import com.joker.model.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository("tableDao")
public class InMemoryTableDao implements  TableDao {

    private static final Logger log = LoggerFactory.getLogger(TableDao.class);

    private static final Map<Long, Table> tables = new ConcurrentHashMap<>();

    @Override
    public void createTable(Room room) {
        Table newTable = null;
        switch (room.getGameMode()){
            case NINES:
                newTable = new GameNines(room.getPlayers(), room.getBayonet());
                log.info("---------- Initialized Nines game mode !!! --------------");
                break;
            case STANDARD:
                newTable = new GameStandard(room.getPlayers(), room.getBayonet());
                log.info("---------- Initialized Standard game mode !!! -------------");
                break;
            default:
                log.error("Unknown game mode");
                return;
        }
        tables.put(room.getId(), newTable);
    }

    @Override
    public Table getTable(long id) {
        return tables.get(id);
    }
}
