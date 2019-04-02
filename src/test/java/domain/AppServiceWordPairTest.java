package domain;

import dao.FakeUserDao;
import dao.FakeWordPairDao;
import org.junit.Before;
import org.junit.Test;
import sanastosovellus.domain.AppService;
import sanastosovellus.domain.User;
import sanastosovellus.domain.WordPair;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AppServiceWordPairTest {

    FakeWordPairDao wordPairDao;
    FakeUserDao userDao;
    AppService service;

    @Before
    public void setUp() {
        wordPairDao = new FakeWordPairDao();
        userDao = new FakeUserDao();
        User user1 = new User("tester1", "testpwd");
        User user2 = new User("tester2", "testpwd");
        userDao.create(user1);
        userDao.create(user2);
        wordPairDao.create(new WordPair(1, "kissa", "cat", user1));

        service = new AppService(wordPairDao, userDao);
        service.login("tester1", "testpwd");
    }

    @Test
    public void atStartListContainsInitializedWordPairs() {
        List<WordPair> wordPairs = service.getPairs();

        assertEquals(1, wordPairs.size());

        WordPair pair = wordPairs.get(0);
        assertEquals("kissa", pair.getWord());
        assertEquals("cat", pair.getTranslation());
        assertEquals("tester1", pair.getUser().getUsername());
    }

    @Test
    public void listIsEmptyIfNotLoggedIn() {
        service.logout();
        List<WordPair> wordPairs = service.getPairs();
        assertEquals(0, wordPairs.size());
    }

    @Test
    public void loggedUsersListContainsAddedWordPairs() {
        service.addWordPair("koira", "dog");
        service.logout();
        service.login("tester2", "testpwd");
        List<WordPair> wordpairs = service.getPairs();
        assertEquals(0, wordpairs.size());
    }

}
