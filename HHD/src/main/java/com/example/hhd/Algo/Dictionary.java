package com.example.hhd.Algo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Dictionary {
    public static final int VI_EN = 0;
    public static final int EN_VI = 1;

    public Dictionary getDictionary_from_file(File file) throws IOException {
        if (Helper.get_extension(file).equals("txt")) {
            return new TrieDictionary(file);
        }
        throw new RuntimeException("can't read file");
    }

    public abstract void insert(Word word);

    public abstract boolean delete(Word word);

    public abstract void insertAndSave(Word word);

    public abstract boolean deleteAndSave(Word word);

    public abstract ArrayList<Word> search(String target);

    public abstract ArrayList<Word> searchWithRecentWord(String target);

    public abstract void loadRecentWord(String data);

    public abstract void addRecentWord(String word);

    public abstract String getRecentWord();

    public abstract boolean contains(String word_target);

    public abstract ArrayList<Word> allWordList();

    public abstract Word randomWord();

    public abstract Word randomWord(int length);

    public abstract void importFromFile(File f) throws IOException;

    public abstract void exportToFile(File f) throws IOException;
}
