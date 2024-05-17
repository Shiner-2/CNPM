package com.example.hhd.Games.Quiz;

import com.example.hhd.App;
import com.example.hhd.Games.GamesController;
import com.example.hhd.SideBar.SideBar;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class QuizGameController extends AnchorPane implements Initializable {
    QuizGame gameData;

    @FXML
    private Label QuestionNumberCnt;
    @FXML
    private Label question;
    @FXML
    private ImageView QuizNextBtn;
    @FXML
    private Label Point;
    @FXML
    private Button choiceA, choiceB, choiceC, choiceD;
    private String normalColor = "#524c49";
    @FXML
    private ImageView imgV1;
    @FXML
    private ImageView imgV2;
    @FXML
    private ImageView imgV3;

    public QuizGameController() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Games/Quiz/Quiz.fxml"));
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

    public void replay() {
        questionCounter = 0;
        score = 0;
        setQuestion();
        initGame();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            gameData = new QuizGame();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        initGame();
        setQuestion();
        GamesController.setHoverEffect(imgV1,imgV2,imgV3);
        imgV1.setOnMouseClicked(event -> {
            SideBar.loadGames();
        });
        imgV2.setOnMouseClicked(event -> {
            replay();
        });
    }

    private int questionCounter = 0;
    public static int score = 0;
    private static final int questionMax = 10;

    public void initGame() {
        QuizNextBtn.setDisable(true);
        QuizNextBtn.setVisible(false);
        Point.setText(""+score*100);
        QuestionNumberCnt.setText(String.format("%d/10",questionCounter));
        gameData.setGame();
    }
    Question currentQuestion;

    // to do: link this to result screen
    public void showResult() {
        QuizNextBtn.setDisable(true);
        QuizNextBtn.setVisible(false);

        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Game Over");
        a.setContentText("You score " + score*100 + " point!!!");
        a.show();
    }

    public void setQuestion() {
        ++ questionCounter;
        QuestionNumberCnt.setText(String.format("%d/10",questionCounter));

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
        choiceA.setStyle("");
        choiceB.setStyle("");
        choiceC.setStyle("");
        choiceD.setStyle("");
        choiceA.setDisable(false);
        choiceB.setDisable(false);
        choiceC.setDisable(false);
        choiceD.setDisable(false);

        if (questionCounter == 10) {
            showResult();
        }
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
