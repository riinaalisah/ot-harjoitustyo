package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *  A class that represents a user
 */


public class User {

    private String username;
    private String password;
    private List<WordPair> wordPairs;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.wordPairs = new ArrayList<>();
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public List getWords() {
        return this.wordPairs;
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
