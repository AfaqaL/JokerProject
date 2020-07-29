package com.joker.controller;

import com.joker.model.TableHistory;
import com.joker.model.User;
import com.joker.model.dto.HistoryResponse;
import com.joker.services.game.GameServiceBean;
import com.joker.services.history.HistoryService;
import com.joker.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
public class HistoriesController {
    private static final Logger log = LoggerFactory.getLogger(GameServiceBean.class);

    @Autowired
    private HistoryService historyService;

    @Autowired
    private UserService userService;

    @GetMapping("/histories")
    public String TableHistories() {
        return "histories/histories";
    }

    @GetMapping("/histories/getHistories")
    public @ResponseBody
    List<HistoryResponse> getUserGames (HttpSession session) {
        long id = ((User)session.getAttribute("user")).getId();

        List<TableHistory> games = historyService.getUserHistory(id);

        List<HistoryResponse> response = new ArrayList<>(4);
        for(TableHistory table : games) {
            HistoryResponse respElem = new HistoryResponse(table);
            respElem.setName1(userService.getUsername(table.getId1()));
            respElem.setName2(userService.getUsername(table.getId2()));
            respElem.setName3(userService.getUsername(table.getId3()));
            respElem.setName4(userService.getUsername(table.getId4()));
            response.add(respElem);
        }
        return response;
    }
}
