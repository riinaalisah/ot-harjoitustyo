package dao;

import domain.WordPair;

import java.util.List;

public interface WordPairDao {

    WordPair create(WordPair pair) throws Exception;
    List<WordPair> getAll();
}
