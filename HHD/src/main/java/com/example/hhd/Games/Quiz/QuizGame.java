package com.example.hhd.Games.Quiz;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static java.util.Arrays.*;

public class QuizGame {
    private ArrayList<Question> questions = new ArrayList<>();
    private int counter = 0;

    public QuizGame() throws IOException {
        readFile(new File("HHD/src/main/resources/data/question.csv"));
    }

    public void readFile(File file) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(file.getPath()), StandardCharsets.UTF_8);

        int count = 0;
        for (String line : lines) {
            String[] f = line.split(",");

            ++ count;

            String question = String.join(",", copyOfRange(f, 0, f.length - 5));
            String[] choices = copyOfRange(f, f.length - 5, f.length - 1);
            String answer = f[f.length - 1];

            if (question.charAt(0) == '\"') {
                question = question.substring(1, question.length() - 1);
            }

//            System.out.println(question);
            questions.add(new Question(question, choices, answer));

            if (count >= 25) break;
        }
    }

    public void setGame() {
        counter = 0;
        Collections.shuffle(questions);
    }

    public Question getQuestion() {
        assert(counter < questions.size());
        return questions.get(counter ++);
    }
}
