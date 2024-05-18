package com.example.hhd.Games.WordPicture;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.util.Pair;

import java.net.URL;
import java.util.*;

public class WordPictureController implements Initializable {
    @FXML
    ImageView imageView;

    @FXML
    Label question;

    @FXML
    Label Point;

    @FXML
    ImageView QuizNextBtn;

    @FXML
    TextField userInputWord;

    Pair<String, Image> q;

    public static int score = 0;

    WordPictureGame data;

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
        setQuestion();
        QuizNextBtn.setOnMouseClicked(mouseEvent -> {
            setQuestion();
        });
    }

    public String getHint(String s) {
        List<String> letters = Arrays.asList(s.split(""));
        Collections.shuffle(letters);
        return String.join("/", letters);
    }

    private void centerImage() {
        Image img = imageView.getImage();
        if (img == null) return ;

        double w = 0;
        double h = 0;

        double ratioX = imageView.getFitWidth() / img.getWidth();
        double ratioY = imageView.getFitHeight() / img.getHeight();

        double reducCoeff = 0;
        if(ratioX >= ratioY) {
            reducCoeff = ratioY;
        } else {
            reducCoeff = ratioX;
        }

        w = img.getWidth() * reducCoeff;
        h = img.getHeight() * reducCoeff;

        imageView.setX((imageView.getFitWidth() - w) / 2);
        imageView.setY((imageView.getFitHeight() - h) / 2);
    }

    public void setQuestion() {
        try {
            q = data.getQuiz();
            question.setText(getHint(q.getKey().toUpperCase()));
            imageView.setImage(q.getValue());

            centerImage();

            QuizNextBtn.setDisable(true);
            userInputWord.setDisable(false);
            question.setStyle("-fx-background-color: WHITE; -fx-background-radius: 10px");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    int questionCount = 0;
    public void showResult(boolean isCorrect) {
        if (isCorrect) {
            question.setText(q.getKey().toUpperCase());
            Point.setText(String.valueOf(score * 100));
            question.setStyle("-fx-background-color: GREEN; -fx-background-radius: 10px");
        } else {
            question.setText(q.getKey().toUpperCase());
            question.setStyle("-fx-background-color: RED; -fx-background-radius: 10px");
        }
        userInputWord.setDisable(true);
        QuizNextBtn.setDisable(false);
        questionCount ++;
//        FXMLLoader fxmlLoader = new FXMLLoader("");
    }
}
