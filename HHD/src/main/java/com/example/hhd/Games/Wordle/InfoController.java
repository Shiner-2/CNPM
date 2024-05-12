package com.example.hhd.Games.Wordle;

import com.example.hhd.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InfoController extends AnchorPane implements Initializable {
    @FXML
    private ImageView img;

    public ImageView getImg() {
        return img;
    }

    public InfoController() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Games/Wordle/Info.fxml"));
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
        img.setOnMouseEntered(event -> {
            try {
                FileInputStream fis = new FileInputStream("HHD/src/main/resources/img/Square Buttons/Colored Square Buttons/X col_Square Button.png");
                Image i = new Image(fis);
                img.setImage(i);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        img.setOnMouseExited(event -> {
            try {
                FileInputStream fis = new FileInputStream("HHD/src/main/resources/img/Square Buttons/Square Buttons/X Square Button.png");
                Image i = new Image(fis);
                img.setImage(i);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

    }
}
