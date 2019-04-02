package dao;

import sanastosovellus.dao.UserDao;
import sanastosovellus.domain.User;

import java.util.ArrayList;
import java.util.List;

public class FakeUserDao implements UserDao {
    List<User> users = new ArrayList<>();

    public FakeUserDao() {
        users.add(new User("tester", "testpwd"));
    }

    @Override
    public User findByUsername(String username) {
        return users.stream().filter(u->u.getUsername().equals(username)).findFirst().orElse(null);
    }

    @Override
    public User create(User user) {
        users.add(user);
        return user;
    }

    @Override
    public List<User> getAll() {
        return users;
    }

}
