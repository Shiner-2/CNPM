package server;

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
        System.out.println("FUCK OFF:" + String.join("\n",  lines));
        int e = lines.get(0).indexOf('/') - 1;
        if (e <= 0) e = lines.get(0).length();

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


//        System.out.println(result.get(0).getWord());
//        System.out.println(result.get(0).getDefinition());
        return result;
    }

    public static void showWord(String word) {
        System.out.println(word); // bold + header font
    }

    public static void showPronunciation(String pronunciation) {
        System.out.println(pronunciation); // sound button
    }

    public static void showType(String type) {
        System.out.println(type); // italic type
    }

    public static void showMeaning(String meaning, int index) {
        System.out.println("\t" + index + ". " + meaning);
    }

    public static void showExample(String example, String meaning) {
        System.out.println("\t\tex. " + example + " : " + meaning);
    }

    public static void showPhrase(String phrase) {
        System.out.println("phrase: " + phrase);
    }

    public static void showWordDefinition(Word Eword) {
        String word = Eword.getWord(), definition = Eword.getDefinition();

        List<String> lines = new ArrayList<>(List.of(definition.split("\n")));

//        System.out.println(definition);
        showWord(word);

        for (int i = 0, countMeaning = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            switch (line.charAt(0)) {
                case '@': // word
                    if (line.charAt(line.length() - 1) == '/') {
                        // pronunciation
                        showPronunciation(line.substring(line.indexOf('/')));
                    }
                    break;
                case '!':
                    showPhrase(line.substring(1).strip());
                    break;
                case '*': // type
                    countMeaning = 0; // number of meaning in same type
                    showType(line.substring(1).strip());
                    break;
                case '-': // meaning
                    if (line.charAt(1) == ' ') {
                        showMeaning(line.substring(1).strip(), ++ countMeaning);
                    }
                    // else malformed input
                    break;
                case '=': // example
                    int separator = line.indexOf('+');
                    String example = line.substring(1, separator - 1);
                    String meaning = line.substring(separator + 1);
                    showExample(example, meaning);
                    break;
                default:
//                    malformed input
//                    System.out.println(line);
//                    throw new RuntimeException("can't read word definition, wrong format!");
            }
        }
    }

    public static ArrayList<String> getRecentWord(File file) {
        return new ArrayList<>();
    }
}
