package domain;

import dao.FakeUserDao;
import dao.FakeWordPairDao;
import org.junit.Before;
import org.junit.Test;
import sanastosovellus.domain.AppService;
import sanastosovellus.domain.User;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class AppServiceUserTest {
    FakeWordPairDao wordPairDao;
    FakeUserDao userDao;
    AppService service;

    @Before
    public void setUp() {
        wordPairDao = new FakeWordPairDao();
        userDao = new FakeUserDao();
        service = new AppService(wordPairDao, userDao);
    }

    @Test
    public void nonexistentUserCantLogIn() {
        boolean res = service.login("nonexistent", "nonee");
        assertFalse(res);
        assertEquals(null, service.getLoggedUser());
    }

    @Test
    public void cantLogInWithWrongPassword() {
        boolean res = service.login("tester1", "wrong");
        assertFalse(res);
    }

    @Test
    public void existingUserCanLogIn() {
        boolean res = service.login("tester", "testpwd");
        assertTrue(res);
        User loggedUser = service.getLoggedUser();
        assertEquals("tester", loggedUser.getUsername());
        assertEquals("testpwd", loggedUser.getPassword());
    }

    @Test
    public void loggedInUserCanLogout() {
        service.login("tester", "testpwd");
        service.logout();
        assertEquals(null, service.getLoggedUser());
    }

    @Test
    public void userCreationFailsIfUsernameNotUnique() {
        boolean res = service.createUser("tester", "testpwd");
        assertFalse(res);
    }

    @Test
    public void canCreateUserAndLogIn() {
        boolean res = service.createUser("newtester", "testpwd");
        assertTrue(res);
        boolean loginOk = service.login("newtester", "testpwd");
        assertTrue(loginOk);
        User loggedIn = service.getLoggedUser();
        assertEquals("newtester", loggedIn.getUsername());
    }
}
