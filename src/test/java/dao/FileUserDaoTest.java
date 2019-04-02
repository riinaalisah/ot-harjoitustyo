package dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import sanastosovellus.dao.FileUserDao;
import sanastosovellus.dao.UserDao;
import sanastosovellus.domain.User;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FileUserDaoTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    File userFile;
    UserDao dao;

    @Before
    public void setUp() throws Exception {
        userFile = testFolder.newFile("testfile_users.txt");

        try (FileWriter fw = new FileWriter(userFile.getAbsolutePath())) {
            fw.write("tester;testpwd\n");
        }

        dao = new FileUserDao(userFile.getAbsolutePath());
    }

    @Test
    public void usersAreReadCorrectly() {
        List<User> users = dao.getAll();
        assertEquals(1, users.size());
        User user = users.get(0);
        assertEquals("tester", user.getUsername());
        assertEquals("testpwd", user.getPassword());
    }

    @Test
    public void existingUserIsFound() {
        User user = dao.findByUsername("tester");
        assertEquals("tester", user.getUsername());
        assertEquals("testpwd", user.getPassword());
    }

    @Test
    public void nonexistentUserIsFound() {
        User user = dao.findByUsername("eivarmaanloydy");
        assertEquals(null, user);
    }

    @Test
    public void savedUserIsFound() throws Exception {
        User newUser = new User("imnew", "mypwd");
        dao.create(newUser);
        User user = dao.findByUsername("imnew");
        assertEquals("imnew", user.getUsername());
        assertEquals("mypwd", user.getPassword());
    }

    @After
    public void tearDown() {
        userFile.delete();
    }

}
