package com.joker.dao.user;

import com.joker.model.User;

import java.sql.SQLException;

public interface UserDao {

    // Convenience methods to search users
    // in the database.

    /**
     * Search user in the database
     * using id of user.
     *
     * @param id - unique number for user
     * @return found user
     */
    User searchById(long id);

    /**
     * Search user in the database
     * using mail of user.
     *
     * @param mail - user mail
     * @return found user
     */
    User searchByMail(String mail);

    /**
     * Search user in the database
     * using username of user.
     *
     * @param username - name of user
     * @return found user
     */
    User searchByUsername(String username);

    /**
     * Search user in the database
     * using usernama and password.
     *
     * @param username - name of user
     * @param password - user password
     * @return found user
     */
    User searchByUsernameAndPassword(String username, String password);

    /**
     * Search user in the database
     * using username and mail.
     *
     * @param username - name of user
     * @param mail     - user mail
     * @return found user
     */
    User searchByUsernameAndMail(String username, String mail);

    /**
     * Adds new user in the database
     *
     * @param user - user to add
     * @return true if user is added
     * in the database, false - otherwise
     */
    boolean addUser(User user);

    // Convenience methods to update user's
    // information in the database.

    /**
     * Changes password for the given user
     *
     * @param user
     * @param newPassword - new password for user
     */
    boolean changePassword(User user, String newPassword);

    /**
     * Changes rank for the given user
     *
     * @param user
     * @param newRank  - new rank for user
     */
    boolean changeRank(User user, int newRank);
}