package sanastosovellus.domain;

public class WordPair {

    private int id;
    private String word;
    private String translation;
    private User user;

    public WordPair(int id, String word, String translation, User user) {
        this.id = id;
        this.word = word;
        this.translation = translation;
        this.user = user;
    }

    public WordPair(String word, String translation, User user) {
        this.word = word;
        this.translation = translation;
        this.user = user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return this.word;
    }

    public String getTranslation() {
        return this.translation;
    }

    public User getUser() {
        return this.user;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof WordPair)) {
            return false;
        }
        WordPair other = (WordPair) o;
        return id == other.getId();
    }
}
