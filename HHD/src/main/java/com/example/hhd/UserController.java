package com.example.hhd;

import com.example.hhd.Database.GameManagement;
import com.example.hhd.Database.UserManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

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
    private String user_name = PublicValue.profile[0];

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

    static UserControllerItem Wordle;
    static UserControllerItem Hangman;
    static UserControllerItem Quiz;
    static UserControllerItem Scrabble;
    static UserControllerItem WordPicture;

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

        try {
            Wordle = new UserControllerItem("HHD/src/main/resources/img/Wordle-Logo.png",200,120,"Wordle",
                    "Best tried", "inf", "Time completed", "0");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            Hangman = new UserControllerItem("HHD/src/main/resources/img/Hangman-logo.png",200,120,"Hangman",
                    "Best tried", "inf", "Time completed", "0");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            Quiz = new UserControllerItem("HHD/src/main/resources/img/QuizGame.png",150,150,"Quiz",
                    "Best score", "0", "Time completed", "0");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            Scrabble = new UserControllerItem("HHD/src/main/resources/img/Scrabble-Logo-2003.png",200,120,"Scrabble",
                    "Best score", "0", "Time completed", "0");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            WordPicture = new UserControllerItem("HHD/src/main/resources/img/WordPicture-Logo.png",150,150,"Capture the Word",
                    "Best score", "0", "Time completed", "0");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        VBox pane = new VBox();
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(10);
        pane.setPrefWidth(900);
        pane.getChildren().addAll(Wordle,Hangman,Quiz,Scrabble,WordPicture);
        StatContainer.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        StatContainer.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        StatContainer.setContent(pane);
    }

    private void setUserName(String s) {
        // TODO: update UserName base on text
        UserManager.updateProfile(PublicValue.username, user_name, "DisplayName");
        PublicValue.profile[0] = user_name;
        AppController.home.UpdateName();
        UserName.setText(user_name + "'s Profile");
    }

    public void updateStat() {
        // wordle 1
        String[] wordleData = GameManagement.getUserGame(Integer.parseInt(PublicValue.user[0]),4);
        Wordle.updateStat(wordleData[2],wordleData[3]);

        String[] hangmanData = GameManagement.getUserGame(Integer.parseInt(PublicValue.user[0]),1);
        Hangman.updateStat(hangmanData[2],hangmanData[3]);

        String[] quizData = GameManagement.getUserGame(Integer.parseInt(PublicValue.user[0]),2);
        Quiz.updateStat(quizData[2],quizData[3]);

        String[] scrabbleData = GameManagement.getUserGame(Integer.parseInt(PublicValue.user[0]),3);
        Scrabble.updateStat(scrabbleData[2],scrabbleData[3]);

        String[] wordPictureData = GameManagement.getUserGame(Integer.parseInt(PublicValue.user[0]),5);
        WordPicture.updateStat(wordPictureData[2],wordPictureData[3]);
    }
}
