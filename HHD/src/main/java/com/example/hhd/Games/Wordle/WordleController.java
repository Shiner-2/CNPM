package com.example.hhd.Games.Wordle;

import com.example.hhd.*;
import com.example.hhd.Algo.Dictionary;
import com.example.hhd.SideBar.SideBar;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class WordleController extends AnchorPane implements Initializable {
    @FXML
    private VBox WordleContainer;
    @FXML
    private Integer sz = 5;
    public static String HiddenWord = "DOGGY";
    public static boolean GameState = false;
    public static Integer GuessCount = 0;
    private Dictionary data = AppController.data;
    public static ArrayList<WordleWordController> board = new ArrayList<>();
    private Popup popup = new Popup();

    @FXML
    private ImageView imgV1;
    @FXML
    private ImageView imgV2;
    @FXML
    private ImageView imgV3;

    public WordleController() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Games/Wordle/Wordle.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void LoadGames(Event event) {
        SideBar.loadGames();
    }

    public static void EndGame(boolean win) {
        for(int i = GuessCount + 1 ; i < 6; i++) {
            board.get(i).setDisable(true);
        }
        if(win) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("You win");
            a.setContentText("WELL PLAYED");
            a.show();
            GameState = true;
        } else{
            if(GameState == true) {
                return;
            }
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("You lose");
            a.setContentText("The Hidden Word is: " + HiddenWord);
            a.show();
        }
    }

    public static void onLoad() {
        board.get(GuessCount).getWordleWordContainer().requestFocus();
    }

    public void LoadGuide() {
        popup.show(WordleContainer.getScene().getWindow()
                ,WordleContainer.localToScreen(-10,-10).getX()
                ,WordleContainer.localToScreen(-10, -10).getY());
    }

    public static void nextWord() {
        GuessCount++;
        if(GuessCount>=6) {
            EndGame(false);
            System.out.println("lose");
            return;
        }
        board.get(GuessCount).getWordleWordContainer().requestFocus();
    }

    public void NewGame() {
        board.clear();
        WordleContainer.getChildren().clear();
        GuessCount = 0;
        HiddenWord = data.randomWord(5).getWord().toLowerCase();
        GameState = false;
        for(int i = 0; i < 6; i++) {
            WordleWordController Word = new WordleWordController();
            Word.setHiddenWord(HiddenWord);
            board.add(Word);
            WordleContainer.getChildren().add(Word);
        }
        onLoad();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NewGame();

        imgV1.setOnMouseEntered(event -> {
            try {
                FileInputStream fis = new FileInputStream("HHD/src/main/resources/img/Square Buttons/Colored Square Buttons/Home col_Square Button.png");
                Image img = new Image(fis);
                imgV1.setImage(img);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        imgV1.setOnMouseExited(event -> {
            try {
                FileInputStream fis = new FileInputStream("HHD/src/main/resources/img/Square Buttons/Square Buttons/Home Square Button.png");
                Image img = new Image(fis);
                imgV1.setImage(img);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });


        imgV2.setOnMouseEntered(event -> {
            try {
                FileInputStream fis = new FileInputStream("HHD/src/main/resources/img/Square Buttons/Colored Square Buttons/Return col_Square Button.png");
                Image img = new Image(fis);
                imgV2.setImage(img);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        imgV2.setOnMouseExited(event -> {
            try {
                FileInputStream fis = new FileInputStream("HHD/src/main/resources/img/Square Buttons/Square Buttons/Return Square Button.png");
                Image img = new Image(fis);
                imgV2.setImage(img);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });


        imgV3.setOnMouseEntered(event -> {
            try {
                FileInputStream fis = new FileInputStream("HHD/src/main/resources/img/Square Buttons/Colored Square Buttons/Info col_Square Button.png");
                Image img = new Image(fis);
                imgV3.setImage(img);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        imgV3.setOnMouseExited(event -> {
            try {
                FileInputStream fis = new FileInputStream("HHD/src/main/resources/img/Square Buttons/Square Buttons/Info Square Button.png");
                Image img = new Image(fis);
                imgV3.setImage(img);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        AnchorPane anchorPane = new AnchorPane();
        try {
            InfoController info = new InfoController();
            anchorPane.getChildren().add(info);
            ImageView imageView = info.getImg();
            imageView.setOnMouseClicked(event -> {
                popup.hide();
            });
            popup.getContent().add(anchorPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
