package com.example.hhd.Games.Hangman;

import com.example.hhd.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class HangmanKeyBoardKeyController extends VBox {
    @FXML
    private Label HangmanKeyBoardKeykey;
    public HangmanKeyBoardKeyController() {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(
                "Games/Hangman/HangmanKeyBoardKey.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.setStyle("-fx-background-color: #5d64b5;");
    }

    public void Correct() {
        Chosed();
    }

    public void Wrong() {
        Chosed();
    }

    public void Chosed() {
        this.setStyle("-fx-background-color: #a7a7c4;");
        this.setDisable(true);
    }

    public void UnChosed() {
        this.setStyle("-fx-background-color: #5d64b5;");
        this.setDisable(false);
    }

    public void setKey(String s) {
        HangmanKeyBoardKeykey.setText(s);
    }

    public String getKey() {
        return HangmanKeyBoardKeykey.getText();
    }
}
