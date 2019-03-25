import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class UserDao implements Dao<User, Integer> {

    private Database database;

    String username;
    String password;
    HashMap<String, String> words;

    public UserDao(Database database) {
        this.database = database;
    }

    @Override
    public User findOne(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM User WHERE id = ?");
        stmt.setInt(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        User user = new User(rs.getInt("id"), rs.getString("username"),
                rs.getString("password"));

        stmt.close();
        rs.close();
        conn.close();
        return user;

    }

    @Override
    public List<User> findAll() throws SQLException {
        return null;
    }

    @Override
    public User saveOrUpdate(User object) throws SQLException {
        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM User WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }
}
