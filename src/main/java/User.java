import java.util.HashMap;

public class User {
    String username;
    String password;
    HashMap<String, String> words;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.words = new HashMap<>();
    }


}
