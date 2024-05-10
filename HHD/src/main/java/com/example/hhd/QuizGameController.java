package com.example.hhd;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class QuizGameController implements Initializable {
    QuizGame gameData;

    @FXML
    private Label question;
    @FXML
    private ImageView QuizNextBtn;
    @FXML
    private Label Point;
    @FXML
    private Button choiceA, choiceB, choiceC, choiceD;

    public void LoadGames(Event event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Games.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Games");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            gameData = new QuizGame();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        QuizNextBtn.setDisable(true);
        QuizNextBtn.setVisible(false);
        Point.setText(""+score*100);
        initGame();
        setQuestion();
    }

    private int questionCounter = 0;
    public static int score = 0;
    private static final int questionMax = 10;

    public void initGame() {
        gameData.setGame();
    }
    Question currentQuestion;

    // to do: link this to result screen
    public void showResult() {

    }

    public void setQuestion() {
        ++ questionCounter;

        if (questionCounter > 10) {
            showResult();
            return;
        }

        Question q = gameData.getQuestion();
        currentQuestion = q;
        question.setText(q.getQuestion());
        question.setWrapText(true);
        String[] choices = q.getChoices();
        choiceA.setText(choices[0]);
        choiceB.setText(choices[1]);
        choiceC.setText(choices[2]);
        choiceD.setText(choices[3]);

        QuizNextBtn.setDisable(true);
        QuizNextBtn.setVisible(false);
        choiceA.setStyle("-fx-background-color: #98b094;");
        choiceB.setStyle("-fx-background-color: #98b094;");
        choiceC.setStyle("-fx-background-color: #98b094;");
        choiceD.setStyle("-fx-background-color: #98b094;");
        choiceA.setDisable(false);
        choiceB.setDisable(false);
        choiceC.setDisable(false);
        choiceD.setDisable(false);
    }

    @FXML
    public void handleChoiceA(ActionEvent event) {
        if (choiceA.getText().equals(currentQuestion.getAnswer())) {
            System.out.println("Correct answer");
            score ++;
        } else {
            System.out.println("Wrong answer");
        }
        showRes();
    }

    @FXML
    public void handleChoiceB(ActionEvent event) {
        if (choiceB.getText().equals(currentQuestion.getAnswer())) {
            System.out.println("Correct answer");
            score ++;
        } else {
            System.out.println("Wrong answer");
        }
        showRes();
    }

    @FXML
    public void handleChoiceC(ActionEvent event) {
        if (choiceC.getText().equals(currentQuestion.getAnswer())) {
            System.out.println("Correct answer");
            score ++;
        } else {
            System.out.println("Wrong answer");
        }
        showRes();
    }

    @FXML
    public void handleChoiceD(ActionEvent event) {
        if (choiceD.getText().equals(currentQuestion.getAnswer())) {
            System.out.println("Correct answer");
            score ++;
        } else {
            System.out.println("Wrong answer");
        }
        showRes();
    }

    public void showRes() {
        choiceA.setStyle("-fx-background-color: #de211b;");
        choiceB.setStyle("-fx-background-color: #de211b;");
        choiceC.setStyle("-fx-background-color: #de211b;");
        choiceD.setStyle("-fx-background-color: #de211b;");

        if(choiceA.getText().equals(currentQuestion.getAnswer())){
            choiceA.setStyle("-fx-background-color: #3bde1b;");
        }

        if(choiceB.getText().equals(currentQuestion.getAnswer())){
            choiceB.setStyle("-fx-background-color: #3bde1b;");
        }

        if(choiceC.getText().equals(currentQuestion.getAnswer())){
            choiceC.setStyle("-fx-background-color: #3bde1b;");
        }

        if(choiceD.getText().equals(currentQuestion.getAnswer())){
            choiceD.setStyle("-fx-background-color: #3bde1b;");
        }

        QuizNextBtn.setVisible(true);
        QuizNextBtn.setDisable(false);

        Point.setText("" + score*100);

        choiceA.setDisable(true);
        choiceB.setDisable(true);
        choiceC.setDisable(true);
        choiceD.setDisable(true);
    }
}
