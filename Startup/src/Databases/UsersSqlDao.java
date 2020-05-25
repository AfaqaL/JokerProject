package Databases;

import HelperClasses.User;

public class UsersSqlDao implements UsersDao {

    @Override
    public boolean createTable() {
        return false;
    }

    @Override
    public User searchById(long id) {
        return null;
    }

    @Override
    public User searchByMail(String mail) {
        return null;
    }

    @Override
    public User searchByUsername(String username) {
        return null;
    }

    @Override
    public User searchByUsernameAndPassword(String username, String password) {
        return null;
    }

    @Override
    public boolean addUser(String username, String password, String mail) {
        return false;
    }

    @Override
    public boolean changePassword(long id) {
        return false;
    }
}
