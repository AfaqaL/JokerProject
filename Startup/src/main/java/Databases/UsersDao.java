package Databases;

import Model.User;

import java.sql.SQLException;

public interface UsersDao {
    /**
     * Creates a new table. Throws
     * sqlException if table can't
     * be created.
     */
    void createTable() throws SQLException;

    /**
     * Drops table. Throws sqlException
     * if table can't be dropped.
     */
    void dropTable() throws SQLException;

    // Convenience methods to search users
    // in the database.
    /**
     * Search user in the database
     * using id of user.
     * @param id - unique number for user
     * @return found user
     */
    User searchById(long id);

    /**
     * Search user in the database
     * using mail of user.
     * @param mail - user mail
     * @return found user
     */
    User searchByMail(String mail);

    /**
     * Search user in the database
     * using username of user.
     * @param username - name of user
     * @return found user
     */
    User searchByUsername(String username);

    /**
     * Search user in the database
     * using usernama and password.
     * @param username - name of user
     * @param password - user password
     * @return found user
     */
    User searchByUsernameAndPassword(String username, String password);

    /**
     * Adds new user in the database
     * @param username - name of user
     * @param password - user password
     * @param mail - user mail
     * @return true if user is added
     * in the database, false - otherwise
     */
    boolean addUser(String username, String password, String mail);

    // Convenience methods to update user's
    // information in the database.
    /**
     * Changes password for the given user
     * @param username - name of user
     * @param newPassword - new password for user
     * @return true if password changed,
     * false - otherwise
     */
    boolean changePassword(String username, String newPassword);

    /**
     * Changes rank for the given user
     * @param username - name of user
     * @param newRank - new rank for user
     * @return true if rank updated,
     * false otherwise
     */
    boolean changeRank(String username, int newRank);
}