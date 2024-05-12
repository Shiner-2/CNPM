package com.example.hhd.Games.Wordle;

import com.example.hhd.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WordleLetterController extends VBox implements Initializable {
    @FXML
    private Label WordleLetter;
    @FXML
    private VBox container;

    public WordleLetterController() {
        // to fix the bug, add App.class. inside FXMLLoader to fix
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Games/Wordle/Wordle-letter.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void setLetter(String s) {
        WordleLetter.setText(s.toUpperCase());
    }

    public void setCorrect() {
        WordleLetter.setStyle("-fx-background-color: green;");
    }

    public void setWrong() {
        WordleLetter.setStyle("-fx-background-color: gray;");
    }

    public void setHas() {
        WordleLetter.setStyle("-fx-background-color: yellow;");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
