package com.example.hhd;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class WordleLetterController extends VBox {
    @FXML
    private Label WordleLetter;

    public WordleLetterController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "Wordle-letter.fxml"));
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
