package com.example.hhd.SideBar;

import com.example.hhd.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SideBarItem extends AnchorPane implements Initializable {
    @FXML
    private ImageView img;
    @FXML
    private Label name;
    @FXML
    private AnchorPane container;

    private int smallsz = 80;
    private int bigsz = 240;
    public SideBarItem() {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("UI/SideBar/SideBarItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void setName(String s) {
        name.setText(s);
    }

    public void setImg(Image i){
        img.setImage(i);
    }

    public void setNoHover() {
        container.getStylesheets().clear();
        container.setPrefWidth(smallsz);
        name.setVisible(false);
    }

    public void setHover() {
        container.getStylesheets().add(App.class.getResource("CSS/Hover.css").toExternalForm());
        container.setPrefWidth(bigsz);
        name.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setNoHover();
        container.setPrefWidth(smallsz);
    }
}
