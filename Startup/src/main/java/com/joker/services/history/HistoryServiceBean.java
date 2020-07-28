package com.joker.services.history;

import com.joker.dao.history.HistoryDao;
import com.joker.dao.user.UserDao;
import com.joker.model.TableHistory;
import com.joker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HistoryServiceBean implements HistoryService {

    @Autowired
    private HistoryDao historyDao;

    @Autowired
    private UserDao userDao;

    @Override
    public List<TableHistory> getUserHistory(long id) {
        return historyDao.getUserHistory(id);
    }

    @Override
    public boolean addHistory(TableHistory history) {
        return historyDao.addHistory(history);
    }

    @Override
    public String getUsername(long id) {
        User user = userDao.searchById(id);
        return user.getUsername();
    }

}
