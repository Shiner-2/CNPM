package com.example.hhd;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

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

    private boolean sealed = false;
    private int point;
    private boolean choosed = false;
    private String character = "?";
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

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }


    public ScrabbleBoardWordController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "ScrabbleBoardWord.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    public void setTW() {
        ScrabbleBoardWordWord.setStyle("-fx-background-color: red;");
        Label lb = new Label();
        lb.setText("TW");
        ScrabbleBoardWordWord.getChildren().add(lb);
    }
    public void setTL() {
        ScrabbleBoardWordWord.setStyle("-fx-background-color: blue;");
        Label lb = new Label();
        lb.setText("TL");
        ScrabbleBoardWordWord.getChildren().add(lb);
    }
    public void setDW() {
        ScrabbleBoardWordWord.setStyle("-fx-background-color: pink;");
        Label lb = new Label();
        lb.setText("DW");
        ScrabbleBoardWordWord.getChildren().add(lb);
    }
    public void setDL() {
        ScrabbleBoardWordWord.setStyle("-fx-background-color: cyan;");
        Label lb = new Label();
        lb.setText("DL");
        ScrabbleBoardWordWord.getChildren().add(lb);
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
        setChoosed(false);
    }
}
