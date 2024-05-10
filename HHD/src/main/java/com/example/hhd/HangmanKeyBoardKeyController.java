package com.example.hhd;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class HangmanKeyBoardKeyController extends VBox {
    @FXML
    private Label HangmanKeyBoardKeykey;
    public HangmanKeyBoardKeyController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "HangmanKeyBoardKey.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.setStyle("-fx-background-color: cyan;");
    }

    public void Correct() {
        this.setStyle("-fx-background-color: green;");
    }

    public void Wrong() {
        this.setStyle("-fx-background-color: red;");
    }

    public void setKey(String s) {
        HangmanKeyBoardKeykey.setText(s);
    }
}
