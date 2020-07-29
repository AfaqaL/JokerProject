package com.joker.services.game;

import com.joker.dao.table.TableDao;
import com.joker.helper.CardHelper;
import com.joker.model.Room;
import com.joker.model.dto.CardDTO;
import com.joker.model.dto.TableResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameServiceBean implements GameService {

    private static final Logger log = LoggerFactory.getLogger(GameServiceBean.class);

    @Autowired
    TableDao tableDao;

    @Override
    public void createTable(Room room) {
        tableDao.createTable(room);
    }

    @Override
    public TableResponse getTable(long tableId, long userId) {
        return tableDao.getTable(tableId).getTable(userId);
    }

    @Override
    public int getVersion(long tableId) {
        return tableDao.getTable(tableId).getVersion();
    }

    @Override
    public void declareNumber(long tableId, int num) {
        tableDao.getTable(tableId).declareNumber(num);
    }

    @Override
    public void putCard(long tableId, CardDTO card) {
        tableDao.getTable(tableId).putCard(CardHelper.fromDTO(card));
    }

    @Override
    public void setSuperiorCard(long tableId, CardDTO card) {
        tableDao.getTable(tableId).setSuperiorCard(CardHelper.fromDTO(card));
    }
}
