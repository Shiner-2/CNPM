package com.example.hhd;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class GamesController {
    @FXML
    private ImageView gamesBackBtn;

    public void LoadApp(Event event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("App.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("HHD");
        stage.setScene(scene);
        stage.show();
    }

    public void LoadWordle(Event event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Wordle.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Wordle");
        stage.setScene(scene);
        stage.show();
    }

    public void LoadQuiz(Event event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Quiz.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Quiz");
        stage.setScene(scene);
        stage.show();
    }

    public void LoadScrabble(Event event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Scrabble.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Scrabble");
        stage.setScene(scene);
        stage.show();
    }

    public void LoadHangman(Event event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Hangman.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hangman");
        stage.setScene(scene);
        stage.show();
    }


}
