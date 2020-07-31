package com.joker.controller;

import com.joker.model.User;
import com.joker.model.dto.HistoryResponse;
import com.joker.services.history.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HistoriesController {

    @Autowired
    private HistoryService historyService;

    @GetMapping("/histories")
    public String TableHistories() {
        return "histories/histories";
    }

    @GetMapping("/histories/get-histories")
    public @ResponseBody List<HistoryResponse> getUserGames (HttpSession session) {
        long id = ((User) session.getAttribute("user")).getId();
        return historyService.getUserHistory(id);
    }
}
