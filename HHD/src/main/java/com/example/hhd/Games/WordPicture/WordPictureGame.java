package com.example.hhd.Games.WordPicture;

import javafx.scene.image.Image;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordPictureGame {
    private final Random rand = new Random();

    private List<String> words = new ArrayList<>();
    public WordPictureGame() {
        try {
            List<String> lines = Files.readAllLines(Path.of("HHD/src/main/resources/data/WordPictureList.txt"), StandardCharsets.UTF_8);
            for (String line : lines) {
                words.add(line.strip());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getWord() {
        return words.get(rand.nextInt(words.size()));
    }
    private Image getImage(String s) throws IOException {
        String newURL = "https://www.googleapis.com/customsearch/v1" +
                "?key=AIzaSyDvED3p1m0Y2l2TE8e5-DbBOkp1O-fkAn8" +
                "&cx=f4316832967884fe0" +
                "&q=" + s.replaceAll(" ", "+") + "+icon" +
                "&searchType=image" +
                "&fileType=png";

        URL url = new URL(newURL);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

        String output;

//        System.out.println(s);
//        System.out.println("Output from Server .... \n");
        while ((output = br.readLine()) != null) {

            if(output.contains("\"thumbnailLink\": \"")){
                String link=output.substring(output.indexOf("\"thumbnailLink\": \"")+("\"thumbnailLink\": \"").length(), output.indexOf("\","));
//                System.out.println(link);       //Will print the google search links

                return new Image(link);
            }
        }
        conn.disconnect();

        return null;
    }

    public Pair<String, Image> getQuiz() throws IOException {
        String word = getWord();
        Image wordImage = getImage(word);

        return new Pair<>(word, wordImage);
    }
}
