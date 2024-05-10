package com.example.hhd.Games.Wordle;

import com.example.hhd.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class WordleLetterController extends VBox {
    @FXML
    private Label WordleLetter;

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
        WordleLetter.setText(s);
    }
}
