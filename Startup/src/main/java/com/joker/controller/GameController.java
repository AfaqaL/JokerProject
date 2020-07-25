package com.joker.controller;

import com.joker.dto.game.DeclareRequest;
import com.joker.dto.game.CardRequest;
import com.joker.model.Table;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class GameController {

    @GetMapping("/table")
    public String tablePage() {
        return "tablePage/table";
    }

    @GetMapping("/table/update")
    public @ResponseBody Table update(HttpSession session) {
        return null;
    }

    @PostMapping("/table/declare")
    public void declare(HttpSession session,
                        @RequestBody DeclareRequest request) {

    }

    @PostMapping("/table/put")
    public void put(HttpSession session,
                    @RequestBody CardRequest request) {

    }

    @PostMapping("/table/set-superior")
    public void setSuperiorCard(HttpSession session,
                                @RequestBody CardRequest request) {

    }

}
