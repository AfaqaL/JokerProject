package Model;

public class User {
    private long id;
    private String username;
    private String mail;
    private String password;
    private int rank;

    private String verificationCode;

    public User(long id, String username, String mail, String password, int rank) {
        this.id = id;
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.rank = rank;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public int getRank() {
        return rank;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof User)) return false;

        User other = (User) obj;

        if (this.id != other.id) return false;
        if (!this.username.equals(other.username)) return false;
        if (!this.mail.equals(other.mail)) return false;
        if (!this.password.equals(other.password)) return false;
        if (this.rank != other.rank) return false;
        return true;
    }
}
