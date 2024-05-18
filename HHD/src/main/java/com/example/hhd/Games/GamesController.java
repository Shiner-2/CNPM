package com.example.hhd.Games;

import com.example.hhd.App;
import com.example.hhd.AppController;
import com.example.hhd.Games.Hangman.HangmanController;
import com.example.hhd.Games.Quiz.QuizGameController;
import com.example.hhd.Games.Scrabble.ScrabbleController;
import com.example.hhd.Games.WordPicture.WordPictureController;
import com.example.hhd.Games.Wordle.WordleController;
import com.example.hhd.SideBar.SideBar;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GamesController extends AnchorPane {
    @FXML
    private ImageView gamesBackBtn;

    private WordleController wordle = new WordleController();
    private ScrabbleController scrabble = new ScrabbleController();
    private QuizGameController quiz = new QuizGameController();
    private HangmanController hangman = new HangmanController();

    private WordPictureController wordpicture = new WordPictureController();

    public GamesController() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Games/Games.fxml"));
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

    public static void homeHover(ImageView imageView) {
        try {
            FileInputStream fis = new FileInputStream("HHD/src/main/resources/img/Square Buttons/Colored Square Buttons/Home col_Square Button.png");
            Image img = new Image(fis);
            imageView.setImage(img);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void homeNotHover(ImageView imageView) {
        try {
            FileInputStream fis = new FileInputStream("HHD/src/main/resources/img/Square Buttons/Square Buttons/Home Square Button.png");
            Image img = new Image(fis);
            imageView.setImage(img);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void replayHover(ImageView imageView) {
        try {
            FileInputStream fis = new FileInputStream("HHD/src/main/resources/img/Square Buttons/Colored Square Buttons/Return col_Square Button.png");
            Image img = new Image(fis);
            imageView.setImage(img);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void replayNoHover(ImageView imageView) {
        try {
            FileInputStream fis = new FileInputStream("HHD/src/main/resources/img/Square Buttons/Square Buttons/Return Square Button.png");
            Image img = new Image(fis);
            imageView.setImage(img);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void infoHover(ImageView imageView) {
        try {
            FileInputStream fis = new FileInputStream("HHD/src/main/resources/img/Square Buttons/Colored Square Buttons/Info col_Square Button.png");
            Image img = new Image(fis);
            imageView.setImage(img);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void infoNoHover(ImageView imageView) {
        try {
            FileInputStream fis = new FileInputStream("HHD/src/main/resources/img/Square Buttons/Square Buttons/Info Square Button.png");
            Image img = new Image(fis);
            imageView.setImage(img);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setHoverEffect(ImageView imgV1, ImageView imgV2, ImageView imgV3) {
        imgV1.setOnMouseEntered(event -> {
            GamesController.homeHover(imgV1);
        });

        imgV1.setOnMouseExited(event -> {
            GamesController.homeNotHover(imgV1);
        });

        imgV2.setOnMouseEntered(event -> {
            GamesController.replayHover(imgV2);
        });

        imgV2.setOnMouseExited(event -> {
            GamesController.replayNoHover(imgV2);
        });

        imgV3.setOnMouseEntered(event -> {
            GamesController.infoHover(imgV3);
        });

        imgV3.setOnMouseExited(event -> {
            GamesController.infoNoHover(imgV3);
        });
    }

    public static void closeInfoHover(ImageView imageView) {
        imageView.setOnMouseEntered(event -> {
            try {
                FileInputStream fis = new FileInputStream("HHD/src/main/resources/img/Square Buttons/Colored Square Buttons/X col_Square Button.png");
                Image i = new Image(fis);
                imageView.setImage(i);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void closeInfoNoHover(ImageView imageView) {
        imageView.setOnMouseExited(event -> {
            try {
                FileInputStream fis = new FileInputStream("HHD/src/main/resources/img/Square Buttons/Square Buttons/X Square Button.png");
                Image i = new Image(fis);
                imageView.setImage(i);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void setUpCloseInfo(ImageView imageView) {
        imageView.setOnMouseEntered(event -> {
            closeInfoHover(imageView);
        });
        imageView.setOnMouseExited(event -> {
            closeInfoNoHover(imageView);
        });
    }

    public void LoadWordle(Event event) {
        AppController.mainscreen.getChildren().clear();
        AppController.mainscreen.getChildren().add(wordle);
        wordle.onLoad();
    }

    public void LoadQuiz(Event event) throws IOException {
        AppController.mainscreen.getChildren().clear();
        AppController.mainscreen.getChildren().add(quiz);
    }

    public void LoadScrabble(Event event) throws IOException {
        AppController.mainscreen.getChildren().clear();
        AppController.mainscreen.getChildren().add(scrabble);
    }

    public void LoadHangman(Event event) throws IOException {
        AppController.mainscreen.getChildren().clear();
        AppController.mainscreen.getChildren().add(hangman);
    }

    public void LoadWordPicture(Event event) throws IOException {
        AppController.mainscreen.getChildren().clear();
        AppController.mainscreen.getChildren().add(wordpicture);
    }
}
