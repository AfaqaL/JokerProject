package com.joker.dao.table;

import com.joker.game.GameNines;
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
            case STANDARD:
                log.error("Standard not implemented yet");
            default:
                log.error("Unknown game mode");
        }
        tables.put(room.getId(), newTable);
    }

    @Override
    public Table getTable(long id) {
        return tables.get(id);
    }
}
