package com.example.hhd.Games;

import com.example.hhd.App;
import com.example.hhd.AppController;
import com.example.hhd.Games.Hangman.HangmanController;
import com.example.hhd.Games.Quiz.QuizGameController;
import com.example.hhd.Games.Scrabble.ScrabbleController;
import com.example.hhd.Games.Wordle.WordleController;
import com.example.hhd.SideBar.SideBar;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.IOException;

public class GamesController extends AnchorPane {
    @FXML
    private ImageView gamesBackBtn;

    private WordleController wordle = new WordleController();
    private ScrabbleController scrabble = new ScrabbleController();
    private QuizGameController quiz = new QuizGameController();
    private HangmanController hangman = new HangmanController();

    public GamesController() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Games/Games.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void LoadGames(Event event) {
        SideBar.loadGames();
    }

    public void LoadWordle(Event event) {
        AppController.mainscreen.getChildren().clear();
        AppController.mainscreen.getChildren().add(wordle);
        wordle.onLoad();
    }

    public void LoadQuiz(Event event) throws IOException {
        AppController.mainscreen.getChildren().clear();
        AppController.mainscreen.getChildren().add(quiz);
    }

    public void LoadScrabble(Event event) throws IOException {
        AppController.mainscreen.getChildren().clear();
        AppController.mainscreen.getChildren().add(scrabble);
    }

    public void LoadHangman(Event event) throws IOException {
        AppController.mainscreen.getChildren().clear();
        AppController.mainscreen.getChildren().add(hangman);
    }


}
