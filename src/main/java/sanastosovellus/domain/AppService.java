package sanastosovellus.domain;

import sanastosovellus.dao.WordPairDao;
import sanastosovellus.dao.UserDao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class responsible for application logic
 */
public class AppService {
    private WordPairDao wordPairDao;
    private UserDao userDao;
    private User loggedIn;

    public AppService(WordPairDao wordPairDao, UserDao userDao) {
        this.wordPairDao = wordPairDao;
        this.userDao = userDao;
    }

    /**
     * Add a new word pair for logged in user
     */
    public boolean addWordPair(String word, String translation) {
        WordPair pair = new WordPair(word, translation, loggedIn);
        try {
            wordPairDao.create(pair);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Delete word pair from users words
     * @param pair
     * @return
     */
    public boolean deleteWordPair(WordPair pair) {
        try {
            wordPairDao.delete(pair);
        } catch (Exception e) {
            return false;
        }
        return true;

    }

    /**
     * Get list of user's word pairs
     * @return List of wordPairs
     */
    public List<WordPair> getPairs() {
        if (loggedIn == null) {
            return new ArrayList<>();
        }

        return wordPairDao.getAll()
                .stream()
                .filter(w-> w.getUser().equals(loggedIn))
                .collect(Collectors.toList());
    }

    /**
     * Login
     */
    public boolean login(String username, String pwd) {
        User user = userDao.findByUsername(username);
        if (user == null) {
            return false;
        }
        if (!pwd.equals(user.getPassword())) {
            return false;
        }
        loggedIn = user;
        return true;
    }

    /**
     * Logged in user
     */
    public User getLoggedUser() {
        return loggedIn;
    }

    /**
     * Logout
     */
    public void logout() {
        loggedIn = null;
    }

    /**
     * Create a new user
     */
    public boolean createUser(String username, String password) {
        if (userDao.findByUsername(username) != null) {
            return false;
        }
        User user = new User(username, password);
        try {
            userDao.create(user);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
