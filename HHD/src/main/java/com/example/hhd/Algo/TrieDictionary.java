package com.example.hhd.Algo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class TrieDictionary extends Dictionary {
    private Trie trie = new Trie();
    private Random rand = new Random();
    private RecentWord recentWord = new RecentWord();
    private File toExportFile = new File("HHD/src/main/resources/data/dictionary.txt");

    public TrieDictionary() throws IOException {
        File file = new File("HHD/src/main/resources/data/vietanh.txt");
        importFromFile(file);
    }

    public TrieDictionary(File file) throws IOException {
        importFromFile(file);
    }

    public TrieDictionary(int mode, boolean firstTime) throws IOException {
        switch (mode) {
            case VI_EN -> {
                trie.clear();
                setToExportFile(new File("HHD/src/main/resources/data/dictionaryViEn.txt"));
                if (firstTime) {
                    importFromFile(new File("HHD/src/main/resources/data/vietanh.txt"));
                    exportToFile(toExportFile);
                }
            }
            case EN_VI -> {
                trie.clear();
                setToExportFile(new File("HHD/src/main/resources/data/dictionaryEnVi.txt"));
                if (firstTime) {
                    importFromFile(new File("HHD/src/main/resources/data/anhviet109K.txt"));
                    exportToFile(toExportFile);
                }
            }
        }
    }

    public void setToExportFile(File file) {
        this.toExportFile = file;
    }

    public void importFromFile(File file) throws IOException {
        for (Word w : Helper.getWordFromFile(file)) {
            insert(w);
//            System.out.println(w.getWord());
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
            exportToFile(toExportFile);
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
            exportToFile(toExportFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return canDeleted;
    }

    @Override
    public ArrayList<Word> search(String target) {
        return trie.search_word(target, 20);
    }

    @Override
    public ArrayList<Word> searchWithRecentWord(String target) {
        ArrayList<String> recent = recentWord.get(target);
        ArrayList<Word> allWord = search(target);

        ArrayList<Word> result = new ArrayList<>();

        for (String w : recent) {
            result.add(trie.search_word(w, 1).get(0));
        }

        for (Word w : allWord) {
            if (!result.contains(w)) {
                result.add(w);
            }
        }

        return result;
    }

    @Override
    public void loadRecentWord(String data) {
        recentWord.importWord(data);
    }

    @Override
    public void addRecentWord(String word) {
        recentWord.addWord(word);
    }

    @Override
    public String getRecentWord() {
        return recentWord.exportWord();
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

        if (words.isEmpty()) {
            return new Word("","");
        }

        return words.get(rand.nextInt(words.size()));
    }
}
