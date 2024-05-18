package com.example.hhd;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;

public class HomeController extends AnchorPane implements Initializable {
    public HomeController() {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(
                "Home.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private Label WelcomeMessage;
    @FXML
    private Label Joke;
    @FXML
    private ListView EnViRecent;
    @FXML
    private ListView ViEnRecent;
    private ArrayList<String> joke = new ArrayList<>();
    private Random random = new Random();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            File file = new File("HHD/src/main/resources/data/joke.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String tmp = scanner.nextLine();
                joke.add(tmp);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        NewJoke();
    }

    @FXML
    public void NewJoke() {
        int rand = random.nextInt(joke.size());
        Joke.setText(joke.get(rand));
    }


}
