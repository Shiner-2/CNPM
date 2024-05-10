package com.example.hhd;

import kotlin.text.Charsets;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Helper {
    /**
     * Get file extension
     * @param file .
     * @return file extension
     */
    public static String get_extension(File file) {
        if (file.isDirectory()) {
            return ""; // no extension
        }
        String name = file.getName();
        int pos = name.lastIndexOf('.');

        if (pos >= 0) {
            return name.substring(pos + 1);
        }
        return ""; // no extension
    }

    /**
     * Get word from lines.
     * @param lines : the lines
     * @return word
     */
    public static Word getWordFromLines(List<String> lines) {
        if (lines.isEmpty() || String.join("", lines).isBlank()) {
            return null;
        }

        int e = lines.get(0).indexOf('/') - 1;
        if (e <= 0 || !lines.get(0).endsWith("/")) {
            e = lines.get(0).length();
        }
        String word = lines.get(0).substring(1, e);

        return new Word(word, String.join("\n", lines));
    }

    public static List<Word> getWordFromFile(File file) throws IOException {
        List<Word> result = new ArrayList<>();
        List<String> lines = new ArrayList<>();

//        System.out.println(Files.readAllLines(Path.of(file.getPath()), Charsets.UTF_8).size());

        for (String dirty_line : Files.readAllLines(Path.of(file.getPath()), Charsets.UTF_8)) {
            String line = dirty_line.replaceAll("[\uFEFF-\uFFFF]", "").strip().trim();

            if (line.isBlank() || line.charAt(0) == '@') {
                Word w = getWordFromLines(lines);
                if (w != null) {
                    result.add(w);
                }
                lines.clear();
            }
            if (!line.isBlank()) {
                lines.add(line);
            }
        }

        if (!lines.isEmpty()) {
            Word w = getWordFromLines(lines);
            if (w != null) {
                result.add(w);
            }
            lines.clear();
        }

        return result;
    }

    public static ArrayList<String> getRecentWord(File file) {
        return new ArrayList<>();
    }
}
