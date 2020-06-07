package Authentication;

import Model.User;

public class Authentication {
    // HashMap < sessionId, user >

    public User checkUsernameAndPassword(String username, String password) {
        return null;
    }

    public User checkUsernameAndMail(String username, String mail) {
        return null;
    }

    public User checkMail(String mail) {
        return null;
    }

    public boolean sendCode(String sessionId, User user) {
        return false;
    }

    public boolean verifyCode(String sessionId, String code) {
        return false;
    }

    public boolean registerUser(String sessionId) {
        return false;
    }

    public boolean sendPassword(User user) {
        return false;
    }

    public boolean changePassword(User user, String newPassword) {
        return false;
    }
}