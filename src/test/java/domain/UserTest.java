package domain;

import org.junit.Test;
import sanastosovellus.domain.User;
import sanastosovellus.domain.WordPair;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class UserTest {

    @Test
    public void equalsWhenSameUsername() {
        User user1 = new User("tester", "testpwd");
        User user2 = new User("tester", "pwd");
        assertTrue(user1.equals(user2));
    }

    @Test
    public void notEqualsWhenDifferentUsername() {
        User user1 = new User("tester", "testpwd");
        User user2 = new User("nottester", "nottestpwd");
        assertFalse(user1.equals(user2));
    }

    @Test
    public void notEqualsWhenrDifferentType() {
        User user1 = new User("tester", "testpwd");
        WordPair pair = new WordPair("kissa", "cat", user1);
        assertFalse(user1.equals(pair));
    }
}
