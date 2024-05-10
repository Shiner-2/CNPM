package com.example.hhd;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

import java.io.IOException;

public class HangmanHiddenLetterController extends AnchorPane {
    @FXML
    private Label HangmanHiddenLetterLetter;
    @FXML
    private Line HangmanHiddenLetterUnderline;
    public String hiden;


    public HangmanHiddenLetterController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "Hangman-Hidden-Letter.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        HangmanHiddenLetterLetter.setText("");
    }

    public void setLetter(String s) {
        HangmanHiddenLetterUnderline.setVisible(false);
        HangmanHiddenLetterLetter.setText(s);
    }

    public String getLetter() {
        return HangmanHiddenLetterLetter.getText();
    }

    public void setNotGuess() {
        HangmanHiddenLetterLetter.setTextFill(Paint.valueOf("red"));
    }

}
