package com.joker.databases;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class InMemoryMailDao {
    private static InMemoryMailDao instance;
    private ArrayList<String> mails;

    public static InMemoryMailDao getInstance() {
        if (instance == null) {
            synchronized (InMemoryMailDao.class) {
                if (instance == null) {
                    instance = new InMemoryMailDao();
                }
            }
        }
        return instance;
    }


    public InMemoryMailDao(){
        mails = new ArrayList<>();
        mails.add("mmukhiguli@gmail.com");
        mails.add("mimukh18@freeuni.edu.ge");
    }

    public boolean mailExists(String mail){
        return mails.contains(mail);
    }
}
