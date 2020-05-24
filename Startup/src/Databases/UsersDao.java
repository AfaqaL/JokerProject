package Databases;

import HelperClasses.User;

public interface UsersDao {
    boolean createTable();

    User searchById(long id);

    User searchByMail(String mail);

    User searchByUsername(String username);

    User searchByUsernameAndPassword(String username, String password);

    boolean addUser(String username, String password, String mail);

    boolean changePassword(long id);
}