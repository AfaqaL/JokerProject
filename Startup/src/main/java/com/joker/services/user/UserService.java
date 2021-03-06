package com.joker.services.user;

import com.joker.model.User;

public interface UserService {

    String getUsername(long id);

    User getById(long id);

    User getByUsernameAndPassword(String username, String password);

    User getByUsernameAndMail(String username, String mail);

    User getByMail(String mail);

    void addUser(User user);

    void changePassword(User user, String newPassword);

    void changeRank(User user, int newRank);
}
