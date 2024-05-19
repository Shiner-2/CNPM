package com.example.hhd.Games.WordPicture;

import com.example.hhd.App;
import com.example.hhd.Database.GameManagement;
import com.example.hhd.Games.GamesController;
import com.example.hhd.PublicValue;
import com.example.hhd.SideBar.SideBar;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class WordPictureController extends AnchorPane implements Initializable {
    @FXML
    private Label QuestionNumberCnt;
    @FXML
    private ImageView Question;
    @FXML
    private Label wordHint;

    @FXML
    Label Point;

    @FXML
    ImageView QuizNextBtn;

    @FXML
    TextField userInputWord;

    @FXML
    ImageView info, reset, home;

    Pair<String, Image> q;

    public static int score = 0;

    WordPictureGame data;

    String[] getUserData = GameManagement.getUserGame(Integer.parseInt(PublicValue.user[0]),5);

    public WordPictureController() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Games/WordPicture/WordPicture.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        GamesController.setHoverEffect(home,reset,info);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        data = new WordPictureGame();
        userInputWord.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                // process submit
                String text = userInputWord.getText();
                if (text.equalsIgnoreCase(q.getKey())) {
                    score ++;
                    showResult(true);
                } else {
                    showResult(false);
                }
                userInputWord.setText("");
            }
        });
        home.setOnMouseClicked(event -> {
            SideBar.loadGames();
        });
        reset.setOnMouseClicked(event -> {
            setUpGame();
        });

        score = questionCount = 0;
        setQuestion();
        QuizNextBtn.setOnMouseClicked(mouseEvent -> {
            setQuestion();
        });
    }

    public void setUpGame() {
        score = questionCount = 0;
        Point.setText("0");
        setQuestion();
    }

    public String getHint(String s) {
        List<String> letters = Arrays.asList(s.split(""));
        Collections.shuffle(letters);
        return String.join("/", letters);
    }

    private void centerImage() {
        Image img = Question.getImage();
        if (img == null) return ;

        double w = 0;
        double h = 0;

        double ratioX = Question.getFitWidth() / img.getWidth();
        double ratioY = Question.getFitHeight() / img.getHeight();

        double reducCoeff = 0;
        if(ratioX >= ratioY) {
            reducCoeff = ratioY;
        } else {
            reducCoeff = ratioX;
        }

        w = img.getWidth() * reducCoeff;
        h = img.getHeight() * reducCoeff;

        Question.setX((Question.getFitWidth() - w) / 2);
        Question.setY((Question.getFitHeight() - h) / 2);
    }

    int questionCount = 0;
    public void setQuestion() {
        ++ questionCount;
        QuestionNumberCnt.setText(String.format("%d/10",questionCount));
        try {
            q = data.getQuiz();
            wordHint.setText(getHint(q.getKey().toUpperCase()));
            Question.setImage(q.getValue());

            centerImage();

            QuizNextBtn.setVisible(false);
            userInputWord.setDisable(false);
            wordHint.setStyle("-fx-background-color: GREEN; -fx-background-radius: 10px");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void showResult(boolean isCorrect) {
        if (isCorrect) {
            wordHint.setText(q.getKey().toUpperCase());
            Point.setText(String.valueOf(score * 100));
            wordHint.setStyle("-fx-background-color: GREEN; -fx-background-radius: 10px");
        } else {
            wordHint.setText(q.getKey().toUpperCase());
            wordHint.setStyle("-fx-background-color: RED; -fx-background-radius: 10px");
        }
        userInputWord.setDisable(true);
        QuizNextBtn.setDisable(false);
        QuizNextBtn.setVisible(true);

        if (questionCount == 10) {
            QuizNextBtn.setDisable(true);
            QuizNextBtn.setVisible(false);

            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Game Over");
            a.setContentText("You score " + score*100 + " point!!!");
            a.show();

            int id = Integer.parseInt(getUserData[0]);
            int hs = Integer.parseInt(getUserData[2]);
            int tw = Integer.parseInt(getUserData[3]) + 1;
            if(tw==1) {
                hs = score * 100;
            } else {
                hs = Math.max(hs,score*100);
            }
            GameManagement.updateUserGame(id,"",hs,tw);
        }
    }
}
