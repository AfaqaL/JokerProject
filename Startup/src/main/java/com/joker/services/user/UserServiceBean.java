package com.joker.services.user;

import com.joker.dao.user.UserDao;
import com.joker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceBean implements UserService {

    @Autowired
    private UserDao userDao;

    public User getByUsernameAndPassword(String username, String password) {
        return userDao.searchByUsernameAndPassword(username, password);
    }

    public User getByUsernameAndMail(String username, String mail) {
        return userDao.searchByUsernameAndMail(username, mail);
    }

    public User getByMail(String mail) {
        return userDao.searchByMail(mail);
    }

    public void addUser(User user) {
        userDao.addUser(user);
    }

    public void changePassword(User user, String newPassword) {
        userDao.changePassword(user, newPassword);
    }

    @Override
    public void changeRank(User user, int newRank) {
        userDao.changeRank(user, newRank);
    }
}

