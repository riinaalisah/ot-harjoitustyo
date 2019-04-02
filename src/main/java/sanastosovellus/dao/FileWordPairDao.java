package sanastosovellus.dao;

import sanastosovellus.domain.User;
import sanastosovellus.domain.WordPair;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileWordPairDao implements WordPairDao {
    public List<WordPair> wordpairs;
    private String file;

    public FileWordPairDao(String file, UserDao users) throws Exception {
        wordpairs = new ArrayList<>();
        this.file = file;
        try {
            Scanner sc = new Scanner(new File(file));
            while (sc.hasNextLine()) {
                String parts[] = sc.nextLine().split(";");
                int id = Integer.parseInt(parts[0]);
                User user = users.getAll().stream().filter(u->u.getUsername().equals(parts[3])).findFirst().orElse(null);
                WordPair wp = new WordPair(id, parts[1], parts[2], user);
                wordpairs.add(wp);
            }
        } catch (Exception e) {
            FileWriter writer = new FileWriter(new File(file));
            writer.close();
        }
    }

    private void save() throws Exception {
        try (FileWriter writer = new FileWriter(new File(file))) {
            for (WordPair wp : wordpairs) {
                writer.write(wp.getId() + ";" + wp.getWord() + ";" + wp.getTranslation() + ";" + wp.getUser().getUsername());
            }
        }
    }

    private int generateId() {
        return wordpairs.size() + 1;
    }

    @Override
    public List<WordPair> getAll() {
        return wordpairs;
    }

    @Override
    public WordPair create(WordPair wp) throws Exception {
        wp.setId(generateId());
        wordpairs.add(wp);
        save();
        return wp;
    }
}
