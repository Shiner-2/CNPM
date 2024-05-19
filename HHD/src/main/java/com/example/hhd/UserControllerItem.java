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

public class UserControllerItem extends AnchorPane {
    @FXML
    private Label Lb;
    @FXML
    private Label Lb1;
    @FXML
    private Label Lb2;
    @FXML
    private Label Lb3;
    @FXML
    private Label Lb4;
    @FXML
    private ImageView GameImg;

    public UserControllerItem(String imgURL, int width, int height, String gameName, String Stat1Name, String Stat1, String Stat2Name, String Stat2) throws FileNotFoundException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(
                "UserItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        FileInputStream fileInputStream = new FileInputStream(imgURL);
        Image img = new Image(fileInputStream,width,height,false,false);

        GameImg.setImage(img);
        Lb.setText(gameName);
        Lb1.setText(Stat1Name);
        Lb2.setText(Stat1);
        Lb3.setText(Stat2Name);
        Lb4.setText(Stat2);
    }

    public void updateStat(String Stat1, String Stat2) {
        Lb2.setText(Stat1);
        Lb4.setText(Stat2);
    }
}
