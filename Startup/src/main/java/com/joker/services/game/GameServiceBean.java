package com.joker.services.game;

import com.joker.dao.table.TableDao;
import com.joker.game.Table;
import com.joker.helper.CardHelper;
import com.joker.model.Room;
import com.joker.model.TableHistory;
import com.joker.model.User;
import com.joker.model.dto.CardDTO;
import com.joker.model.dto.TableResponse;
import com.joker.services.history.HistoryService;
import com.joker.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceBean implements GameService {

    @Autowired
    private TableDao tableDao;

    @Autowired
    private UserService userService;

    @Autowired
    private HistoryService historyService;

    @Override
    public void createTable(Room room) {
        tableDao.createTable(room);
    }

    @Override
    public int getIndex(long tableId, long userId) {
        return tableDao.getTable(tableId).getIndex(userId);
    }

    @Override
    public TableResponse getTable(long tableId, long userId) {
        Table table = tableDao.getTable(tableId);
        TableResponse response = table.getTable(userId);

        if (response.isGameFinished()) {
            historyService.addHistory(createHistory(tableId, response.getFinalScores(), table.getUserIds()));
            changeRank(table.getUserIds(), response.getFinalScores());
        }
        return response;
    }

    @Override
    public int getVersion(long tableId) {
        return tableDao.getTable(tableId).getVersion();
    }

    @Override
    public void declareNumber(long tableId, int num, long playerId) {
        tableDao.getTable(tableId).declareNumber(num, playerId);
    }

    @Override
    public void putCard(long tableId, CardDTO card, long playerId) { tableDao.getTable(tableId).putCard(CardHelper.fromDTO(card), playerId); }

    @Override
    public void setSuperiorCard(long tableId, CardDTO card) { tableDao.getTable(tableId).setSuperiorCard(CardHelper.fromDTO(card)); }

    private TableHistory createHistory(long tableId, List<Integer> finalScores, List<Long> userIds) {
        TableHistory history = new TableHistory();
        history.setTableId(tableId);

        history.setId1(userIds.get(0));
        history.setId2(userIds.get(0));
        history.setId3(userIds.get(0));
        history.setId4(userIds.get(0));

        history.setScore1(finalScores.get(0));
        history.setScore2(finalScores.get(1));
        history.setScore3(finalScores.get(2));
        history.setScore4(finalScores.get(3));

        return history;
    }

    private void changeRank(List<Long> userIds, List<Integer> finalScores) {
        for (int i = 0; i < Table.NUM_PLAYERS; i++) {
            User user = userService.getById(userIds.get(i));
            userService.changeRank(user, user.getRank() + finalScores.get(i));
        }
    }
}
