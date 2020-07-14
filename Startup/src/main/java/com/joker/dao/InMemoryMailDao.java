package com.joker.dao;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class InMemoryMailDao {
    private ArrayList<String> mails;


    public InMemoryMailDao(){
        mails = new ArrayList<>();
        mails.add("mmukhiguli@gmail.com");
        mails.add("mimukh18@freeuni.edu.ge");
    }

    public boolean mailExists(String mail){
        return mails.contains(mail);
    }

}
