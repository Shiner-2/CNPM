package com.example.hhd;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.io.File;
import java.util.Locale;
import java.util.Random;

public class TrieDictionary extends Dictionary {

    private final Trie trie = new Trie();
    private Random rand = new Random();

    public TrieDictionary() throws IOException {
        File file = new File("HHD/src/main/resources/data/dictionary.txt");
        importFromFile(file);
    }

    public TrieDictionary(File file) throws IOException {
        importFromFile(file);
    }

    public void importFromFile(File file) throws IOException {
        for (Word w : Helper.getWordFromFile(file)) {
            insert(w);
        }
    }

    public void exportToFile(File file) throws IOException {
        ArrayList<Word> words = allWordList();

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));

        for (Word w : words) {
            writer.write(w.getDefinition());
            writer.write('\n');
            writer.write('\n');
        }
        writer.close();
    }

    @Override
    public void insert(Word word) {
        trie.insert_word(word);
    }

    @Override
    public void insertAndSave(Word word) {
        trie.insert_word(word);
        try {
            exportToFile(new File("HHD/src/main/resources/data/dictionary.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean delete(Word word) {
        return trie.delete_word(word);
    }

    @Override
    public boolean deleteAndSave(Word word) {
        boolean canDeleted = trie.delete_word(word);
        try {
            exportToFile(new File("HHD/src/main/resources/data/dictionary.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return canDeleted;
    }

    @Override
    public ArrayList<Word> search(String target) {
        return trie.search_word(target, 20);
    }

    public ArrayList<Word> allWordList() {
        return trie.search_all_word("");
    }
    @Override
    public boolean contains(String s) {
        return trie.find_word(s.toLowerCase(Locale.ROOT));
    }

    @Override
    public Word randomWord() {
        ArrayList<Word> allWord = allWordList();
        ArrayList<Word> words = new ArrayList<>();

        for (Word w : allWord) {
            if (w.getWord().chars().allMatch(Character::isLetter)) {
                words.add(w);
            }
        }

        return words.get(rand.nextInt(words.size()));
    }

    @Override
    public Word randomWord(int length) {
        ArrayList<Word> allWord = allWordList();
        ArrayList<Word> words = new ArrayList<>();

        for (Word w : allWord) {
            if (w.getWord().chars().allMatch(Character::isLetter) && w.getWord().length() == length) {
                words.add(w);
            }
        }

        return words.get(rand.nextInt(words.size()));
    }
}
