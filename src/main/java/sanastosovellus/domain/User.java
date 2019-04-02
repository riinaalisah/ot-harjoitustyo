package sanastosovellus.domain;

import java.util.ArrayList;
import java.util.List;

/**
 *  A class that represents a user
 */


public class User {

    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User)) {
            return false;
        }

        User other = (User) o;
        return username.equals(other.username);
    }

}
