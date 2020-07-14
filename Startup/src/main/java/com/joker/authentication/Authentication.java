package com.joker.authentication;

import com.joker.dao.UsersSqlDao;
import com.joker.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Authentication {
    private Map<Long, User> sessionIdUser = new HashMap<>();

    private UsersSqlDao dao;

    public User checkUsernameAndPassword(String username, String password) {
        User user = dao.searchByUsernameAndPassword(username,password);
        return user;
    }

    public User checkUsernameAndMail(String username, String mail) {
        User user1 = dao.searchByUsername(username);
        User user2 = dao.searchByMail(mail);
        if(user1 != null)
            return user1;
        return user2;
    }

    public User checkMail(String mail) {
        User user = dao.searchByMail(mail);

        return user;
    }


    public boolean sendCode(String sessionId, User user) {
        return false;
    }

    public boolean verifyCode(String sessionId, String code) {
        return false;
    }

    public boolean registerUser(String sessionId) {
        User user = sessionIdUser.get(sessionId);
        return user != null;
    }

    private String generatePassword() {
        Random rand = new Random();
        StringBuilder password = new StringBuilder();
        int k;
        for (int i = 0; i <8 ; i++) {
            k = rand.nextInt()% 26;
            if(i<4)
                k +='a';
            else if(i < 6)
                k +='A';
            else
                k = k % 17 + '!';
            password.append((char) k);
        }



        return password.toString();
    }

    public String sendPassword(User user) {
        return generatePassword();
    }

    public boolean changePassword(User user, String newPassword) {

        return dao.changePassword(user.getUsername(), newPassword);
    }
}

