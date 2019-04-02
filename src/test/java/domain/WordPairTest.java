package domain;

import org.junit.Test;
import sanastosovellus.domain.User;
import sanastosovellus.domain.WordPair;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class WordPairTest {

    @Test
    public void equalsWhenSameId() {
        WordPair pair1 = new WordPair(1, null, null, null);
        WordPair pair2 = new WordPair(1, null, null, null);
        assertTrue(pair1.equals(pair2));
    }
    
    @Test
    public void notEqualsWhenDifferentId() {
        WordPair pair1 = new WordPair(1, null, null, null);
        WordPair pair2 = new WordPair(2, null, null, null);
        assertFalse(pair1.equals(pair2));
    }

    @Test
    public void notEqualsWhenDifferentType() {
        WordPair pair = new WordPair(1, null, null, null);
        User user = new User(null, null);
        assertFalse(pair.equals(user));
    }
}
