package com.example.hhd.Games.Scrabble;

import com.example.hhd.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ScrabbleBoardWordController extends AnchorPane {
    @FXML
    private AnchorPane ScrabbleBoardWordWord;
    @FXML
    private AnchorPane Overlay;
    @FXML
    private ImageView ImageOverlay;
    @FXML
    private Label lb;

    private boolean sealed = false;
    private int point;
    private boolean choosed = false;
    private String character = "?";
    public int pos = 0;
    private int Xpos;
    private int Ypos;

    public int getYpos() {
        return Ypos;
    }

    public void setYpos(int ypos) {
        Ypos = ypos;
    }

    public int getXpos() {
        return Xpos;
    }

    public void setXpos(int xpos) {
        Xpos = xpos;
    }

    public boolean isChoosed() {
        return choosed;
    }

    public void setChoosed(boolean choosed) {
        this.choosed = choosed;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {

        this.character = character;
    }

    public boolean isSealed() {
        return sealed;
    }

    public void setSealed() {
        this.sealed = true;
    }

    public void setUnSealed() {
        this.sealed = false;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }


    public ScrabbleBoardWordController() {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(
                "Games/Scrabble/ScrabbleBoardWord.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    public void setTW() {
        ScrabbleBoardWordWord.setStyle("-fx-background-color: #ff9e78; -fx-border-color: white; -fx-border-width: 0.5;");
        lb.setText("3W");
        lb.setTextFill(Paint.valueOf("#f67c4b"));
    }
    public void setTL() {
        ScrabbleBoardWordWord.setStyle("-fx-background-color: #ffd05f; -fx-border-color: white; -fx-border-width: 0.5;");
        lb.setText("3L");
        lb.setTextFill(Paint.valueOf("#f8b20d"));
    }
    public void setDW() {
        ScrabbleBoardWordWord.setStyle("-fx-background-color: #aef6ab; -fx-border-color: white; -fx-border-width: 0.5;");
        lb.setText("2W");
        lb.setTextFill(Paint.valueOf("#83e07b"));
    }
    public void setDL() {
        ScrabbleBoardWordWord.setStyle("-fx-background-color: #abe6ff; -fx-border-color: white; -fx-border-width: 0.5;");
        lb.setText("2L");
        lb.setTextFill(Paint.valueOf("#72d0f6"));
    }

    public void setDragEnter() {
        Overlay.setVisible(true);
    }

    public void setDragExit() {
        Overlay.setVisible(false);
    }

//    public void setImage(ImageView imageView) {
//        ImageOverlay.setVisible(true);
//        ImageOverlay.setImage(imageView.getImage());
//    }

    public void setImage(String s) throws FileNotFoundException {
        String path = "HHD/src/main/resources/Scrabble/Image/PNG/Blue/letter_" + s.toUpperCase() + ".png";
        FileInputStream fis = new FileInputStream(path);
        Image img = new Image(fis,50,50,false,false);
        ImageOverlay.setVisible(true);
        ImageOverlay.setImage(img);
    }

    public void retreat(){
        ImageOverlay.setVisible(false);
        character = "?";
        pos = -1;
        setChoosed(false);
    }
}
