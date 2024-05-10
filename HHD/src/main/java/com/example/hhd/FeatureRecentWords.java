package com.example.hhd;
import java.io.*;
import java.util.*;

public class FeatureRecentWords {
    private ArrayList<String> words = new ArrayList<>();
    public void insert(String w) {
        words.remove(w);
        words.add(w);
        if (words.size() > 100) {
            words.remove(0);
        }
    }

    public void importFromFile(File f) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;
        while ((line = br.readLine()) != null) {
            insert(line);
        }
        br.close();
    }

    public void exportToFile(File f) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        for (String word : words) {
            bw.write(word);
            bw.newLine();
        }
        bw.close();
    }
    public ArrayList<String> search(String s) {
        ArrayList<String> result = new ArrayList<>();
        for (String word : words) {
            if (word.startsWith(s)) {
                result.add(word);
            }
        }
        return result;
    }
}
