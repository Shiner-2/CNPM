package com.example.hhd;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserController extends AnchorPane implements Initializable {

    @FXML
    private ScrollPane StatContainer;
    @FXML
    private AnchorPane EditContainer;
    @FXML
    private ImageView EditImg;
    @FXML
    private TextField UserName;
    FileInputStream fileInputStream1 = new FileInputStream("HHD/src/main/resources/img/UI_button/edit1.png");
    private Image HoverImage = new Image(fileInputStream1, 70, 70, false, false);
    FileInputStream fileInputStream = new FileInputStream("HHD/src/main/resources/img/UI_button/edit.png");
    private Image NotHoverimage = new Image(fileInputStream, 70, 70, false, false);
    private String user_name = "Shiner2";

    public UserController() throws FileNotFoundException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(
                "User.fxml"));
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
        EditContainer.setOnMouseEntered(event -> {
            EditImg.setImage(HoverImage);
        });
        EditContainer.setOnMouseExited(event -> {
            EditImg.setImage(NotHoverimage);
        });
        EditContainer.setOnMouseClicked(event -> {
            if(!UserName.isEditable()){
                UserName.setEditable(true);
                UserName.setStyle("-fx-background-color: white;");
                UserName.setText(user_name);
            }else{
                UserName.setEditable(false);
                user_name = UserName.getText();
                UserName.setStyle("-fx-background-color: transparent;");
                setUserName(user_name);
            }
        });
        setUserName(user_name);
    }

    private void setUserName(String s) {
        // TODO: update UserName base on text
        UserName.setText(user_name + "'s Profile");
    }
}
