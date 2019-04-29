package sanastosovellus.domain;

/**
 * Class for a word pair
 */
public class WordPair {

    private int id;
    private String word;
    private String translation;
    private User user;

    /**
     * Constructor
     * @param id id of word pair
     * @param word word
     * @param translation translation of word
     * @param user user who adds the word pair
     */
    public WordPair(int id, String word, String translation, User user) {
        this.id = id;
        this.word = word;
        this.translation = translation;
        this.user = user;
    }

    /**
     * Constructor without id
     * @param word word
     * @param translation translation of word
     * @param user user who adds the word pair
     */
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
