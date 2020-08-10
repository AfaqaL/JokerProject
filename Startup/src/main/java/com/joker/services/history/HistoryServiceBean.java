package com.joker.services.history;

import com.joker.dao.history.HistoryDao;
import com.joker.model.TableHistory;
import com.joker.model.dto.HistoryResponse;
import com.joker.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HistoryServiceBean implements HistoryService {

    @Autowired
    private HistoryDao historyDao;

    @Autowired
    private UserService userService;

    @Override
    public List<HistoryResponse> getUserHistory(long id) {
        List<TableHistory> games = historyDao.getUserHistory(id);

        List<HistoryResponse> result = new ArrayList<>(4);
        for(TableHistory table : games) {
            HistoryResponse respElem = new HistoryResponse(table);
            respElem.setName1(userService.getUsername(table.getId1()));
            respElem.setName2(userService.getUsername(table.getId2()));
            respElem.setName3(userService.getUsername(table.getId3()));
            respElem.setName4(userService.getUsername(table.getId4()));
            result.add(respElem);
        }
        return result;
    }

    @Override
    public boolean addHistory(TableHistory history) {
        if (historyDao.historyExists(history.getTableId())) {
            return false;
        }
        return historyDao.addHistory(history);
    }
}
