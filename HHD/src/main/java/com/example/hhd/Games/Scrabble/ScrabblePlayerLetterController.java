package com.example.hhd.Games.Scrabble;

import com.example.hhd.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ScrabblePlayerLetterController extends ImageView {
    @FXML
    private ImageView ScrabblePlayerLetterLetter;
    private String curLetter;
    public int pos = 0;

    public ScrabblePlayerLetterController() {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(
                "Games/Scrabble/ScrabblePlayerLetter.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void setChar(String s) throws FileNotFoundException {
        setCurLetter(s);
        String path = "HHD/src/main/resources/Scrabble/Image/PNG/Blue/letter_" + s.toUpperCase() + ".png";
        FileInputStream fis = new FileInputStream(path);
        Image img = new Image(fis,70,70,false,false);
        ScrabblePlayerLetterLetter.setImage(img);
    }

    public String getCurLetter() {
        return curLetter;
    }

    public void setCurLetter(String curLetter) {
        this.curLetter = curLetter;
    }


}
