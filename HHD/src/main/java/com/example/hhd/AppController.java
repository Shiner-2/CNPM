package com.example.hhd;

import com.example.hhd.Algo.Dictionary;
import com.example.hhd.Algo.TrieDictionary;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class AppController {

    private Stage stage;
    private Scene scene;
    private FXMLLoader fxmlLoader;
    public static Dictionary data;

    public AppController() throws IOException {
        data = new TrieDictionary();
    }

    public void LoadGames(ActionEvent event) throws IOException {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        fxmlLoader = new FXMLLoader(App.class.getResource("Games/Games.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage.setTitle("Games");
        stage.setScene(scene);
        stage.show();
    }

    public void LoadDictionary(ActionEvent event) throws IOException {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        fxmlLoader = new FXMLLoader(App.class.getResource("Dictionary/Dictionary.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage.setTitle("Dictionary");
        stage.setScene(scene);
        stage.show();
    }

    public void LoadTranslator(ActionEvent event) throws IOException {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        fxmlLoader = new FXMLLoader(App.class.getResource("Translator.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage.setTitle("Translator");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void about(Event event) {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("About us");
        info.setContentText("This project is made for education purpose, by HHD group in UET university");
        info.show();
    }


}
