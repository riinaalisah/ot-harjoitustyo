package sanastosovellus.dao;

import sanastosovellus.domain.WordPair;

import java.util.List;

public interface WordPairDao {

    WordPair create(WordPair pair) throws Exception;

    List<WordPair> getAll();

    boolean delete(WordPair pair);
}
