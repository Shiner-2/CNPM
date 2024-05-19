package com.example.hhd;

import com.example.hhd.Algo.Dictionary;
import com.example.hhd.Algo.Word;
import com.example.hhd.Database.UserManager;
import com.example.hhd.Dictionary.DictionaryController;
import com.example.hhd.SideBar.SideBar;
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

    static Dictionary dataVI_EN = PublicValue.dataVI_EN;
    static Dictionary dataEN_VI = PublicValue.dataEN_VI;

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
        WelcomeMessage.setText("Welcome " + PublicValue.profile[0] + " to our App!");

        updateRecentWord();

        EnViRecent.setOnMouseClicked(mouseEvent -> {
            String currentString = (String) EnViRecent.getSelectionModel().getSelectedItem();
            AppController.dictionary.setMode(Dictionary.EN_VI);

            Word currentWord = dataEN_VI.search(currentString).get(0);

            AppController.dictionary.showWordDefinition(currentWord);
            AppController.dictionary.setRecommendWord(currentString);

            dataEN_VI.addRecentWord(currentString);
            UserManager.updateProfile(PublicValue.username, dataEN_VI.getRecentWord(), "RecentWord_En_Vi");

//            AppController.mainscreen.getChildren().clear();
//            AppController.mainscreen.getChildren().add(AppController.dictionary);
            SideBar.loadDictionary();
        });

        ViEnRecent.setOnMouseClicked(mouseEvent -> {
            String currentString = (String) ViEnRecent.getSelectionModel().getSelectedItem();
            AppController.dictionary.setMode(Dictionary.VI_EN);

            Word currentWord = dataVI_EN.search(currentString).get(0);

            AppController.dictionary.showWordDefinition(currentWord);
            AppController.dictionary.setRecommendWord(currentString);

            dataVI_EN.addRecentWord(currentString);
            UserManager.updateProfile(PublicValue.username, dataVI_EN.getRecentWord(), "RecentWord_Vi_En");

//            AppController.mainscreen.getChildren().clear();
//            AppController.mainscreen.getChildren().add(AppController.dictionary);
            SideBar.loadDictionary();
        });
    }

    @FXML
    public void NewJoke() {
        int rand = random.nextInt(joke.size());
        Joke.setText(joke.get(rand));
    }

    public void UpdateName() {
        WelcomeMessage.setText("Welcome " + PublicValue.profile[0] + " to our App!");
    }


    public void updateRecentWord() {
        EnViRecent.getItems().clear();
        ViEnRecent.getItems().clear();
        EnViRecent.getItems().addAll(dataEN_VI.getRecentWord().split("\n"));
        ViEnRecent.getItems().addAll(dataVI_EN.getRecentWord().split("\n"));
    }

}
