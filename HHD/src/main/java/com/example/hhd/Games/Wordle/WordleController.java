package com.example.hhd.Games.Wordle;

import com.example.hhd.*;
import com.example.hhd.Algo.Dictionary;
import com.example.hhd.Algo.TrieDictionary;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class WordleController{
    @FXML
    private Button btn;
    @FXML
    private VBox WordleContainer;
    @FXML
    private TextField WordleInput;
    private Integer sz = 5;
    private String HiddenWord = "DOGGY";
    private Integer GuessCount = 0;
    private Dictionary data = AppController.data;

    public WordleController() throws IOException {
        HiddenWord = data.randomWord(5).getWord().toLowerCase();
    }

    public void LoadGames(Event event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Games/Games.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Games");
        stage.setScene(scene);
        stage.show();
    }

    public void onWordleBtnSubmit() {
        String in = WordleInput.getText();
        //in = in.toUpperCase();
        if(in.length() != sz) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Too much letters");
            a.setContentText("Please enter " + sz.toString() + " letters!");
            a.show();
        } else{
            if(data.contains(in)) {
                WordleWordController lt = new WordleWordController(getSz());
                lt.setWord(in, HiddenWord);
                WordleContainer.getChildren().add(lt);
                GuessCount++;
            } else{
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText("Invalid Word");
                a.setContentText("Please enter an English word");
                a.show();
            }
        }

        if(GuessCount >= 6) {
            WordleInput.setDisable(true);
            btn.setDisable(true);
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("You lose");
            a.setContentText("The hidden word is: "+HiddenWord);
            a.show();
        }
    }

    public void setSz(Integer sz) {
        this.sz = sz;
    }

    public Integer getSz() {
        return sz;
    }

}
