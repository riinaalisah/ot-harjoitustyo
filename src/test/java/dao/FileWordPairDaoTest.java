package dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import sanastosovellus.dao.FileWordPairDao;
import sanastosovellus.dao.UserDao;
import sanastosovellus.dao.WordPairDao;
import sanastosovellus.domain.User;
import sanastosovellus.domain.WordPair;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import static org.junit.Assert.*;

public class FileWordPairDaoTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    File userFile;
    WordPairDao dao;

    @Before
    public void setUp() throws Exception {
        userFile = testFolder.newFile("testfile_wordpairs.txt");

        UserDao userDao = new FakeUserDao();
        userDao.create(new User("tester", "testpwd"));


        try (FileWriter fw = new FileWriter(userFile.getAbsolutePath())) {
            fw.write("1;kissa;cat;tester\n");
        }

        dao = new FileWordPairDao(userFile.getAbsolutePath(), userDao);
    }

    @Test
    public void wordPairsAreReadCorrectly() {
        List<WordPair> wordpairs = dao.getAll();
        assertEquals(1, wordpairs.size());
        WordPair pair = wordpairs.get(0);
        assertEquals(1, pair.getId());
        assertEquals("kissa", pair.getWord());
        assertEquals("cat", pair.getTranslation());
        assertEquals("tester", pair.getUser().getUsername());
    }

    @Test
    public void addedWordPairsAreListed() throws Exception {
        dao.create(new WordPair("koira", "dog", new User("tester", "testpwd")));
        List<WordPair> wordPairs = dao.getAll();
        assertEquals(2, wordPairs.size());
        WordPair pair = wordPairs.get(1);
        assertEquals("koira", pair.getWord());
        assertEquals("dog", pair.getTranslation());
        assertNotEquals(1, pair.getId());
        assertEquals("tester", pair.getUser().getUsername());
    }

    @Test
    public void wordPairsAreDeleted() throws Exception {
        WordPair pair = new WordPair("testi", "test", new User("tester", "testpwd"));
        dao.create(pair);
        List<WordPair> pairs = dao.getAll();
        assertEquals(2, pairs.size());
        dao.delete(pair);
        pairs = dao.getAll();
        assertEquals(1, pairs.size());
    }

    @After
    public void tearDown() {
        userFile.delete();
    }

}
