package dao;

import sanastosovellus.dao.WordPairDao;
import sanastosovellus.domain.WordPair;

import java.util.ArrayList;
import java.util.List;

public class FakeWordPairDao implements WordPairDao {

    List<WordPair> wordPairs;

    public FakeWordPairDao() {
        wordPairs = new ArrayList<>();
    }

    @Override
    public List<WordPair> getAll() {
        return wordPairs;
    }

    @Override
    public WordPair create(WordPair pair) {
        pair.setId(wordPairs.size() + 1);
        wordPairs.add(pair);
        return pair;
    }

    @Override
    public boolean delete(WordPair pair) {
        wordPairs.remove(pair);
        return true;
    }
}
