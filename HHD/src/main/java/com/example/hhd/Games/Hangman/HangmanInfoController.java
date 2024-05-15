package com.example.hhd.Games.Hangman;

import com.example.hhd.App;
import com.example.hhd.Games.GamesController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HangmanInfoController extends AnchorPane implements Initializable {
    @FXML
    private ImageView img;

    public ImageView getImg() {
        return img;
    }

    public HangmanInfoController() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Games/Hangman/Info.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GamesController.setUpCloseInfo(img);
    }
}
