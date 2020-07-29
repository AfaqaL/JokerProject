package com.joker.dao.table;

import com.joker.game.Table;
import com.joker.model.Room;

public interface TableDao {

    void createTable(Room room);

    Table getTable(long id);
}
