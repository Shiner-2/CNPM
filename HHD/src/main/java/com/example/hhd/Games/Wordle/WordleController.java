package com.example.hhd.Games.Wordle;

import com.example.hhd.*;
import com.example.hhd.Algo.Dictionary;
import com.example.hhd.Algo.TrieDictionary;
import com.example.hhd.SideBar.SideBar;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class WordleController extends AnchorPane {
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
