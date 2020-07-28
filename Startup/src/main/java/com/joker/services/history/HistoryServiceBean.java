package com.joker.services.history;

import com.joker.dao.history.HistoryDao;
import com.joker.dao.user.UserDao;
import com.joker.model.TableHistory;
import com.joker.model.User;
import com.joker.services.game.GameServiceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HistoryServiceBean implements HistoryService {

    private static final Logger log = LoggerFactory.getLogger(GameServiceBean.class);
    public static int n_Call = 0;

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
        ++n_Call;
        User user = userDao.searchById(id);
        log.info("History Service .getUsername: N - " + n_Call);
        return user.getUsername();
    }

}
