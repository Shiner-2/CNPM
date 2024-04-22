package server;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Dictionary {
    public Dictionary getDictionary_from_file(File file) throws IOException {
        if (Helper.get_extension(file).equals("txt")) {
            return new TrieDictionary(file);
        }
        throw new RuntimeException("can't read file");
    }

    public abstract void insert(Word word);

    public abstract boolean delete(Word word);

    public abstract ArrayList<Word> search(String target);

    public abstract boolean contains(String word_target);

    public abstract ArrayList<Word> allWordList();
}
