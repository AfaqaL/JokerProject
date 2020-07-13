package com.joker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/createTable")
public class CreateTableController {
    @GetMapping
    public String getCreateTable() {
        return "tablePage/createTable";
    }
}
