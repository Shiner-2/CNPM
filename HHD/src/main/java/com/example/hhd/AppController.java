package com.example.hhd;

import com.example.hhd.Algo.Dictionary;
import com.example.hhd.Algo.TrieDictionary;
import com.example.hhd.Dictionary.DictionaryController;
import com.example.hhd.Games.GamesController;
import com.example.hhd.SideBar.SideBar;
import com.example.hhd.Translator.TranslatorController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    private Stage stage;
    private Scene scene;
    private FXMLLoader fxmlLoader;

    public static Dictionary data;

    static {
        try {
            data = new TrieDictionary();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private AnchorPane SideBarContainer;
    @FXML
    private AnchorPane MainScreen;
    public static AnchorPane mainscreen;

    private SideBar sideBar;

    public static DictionaryController dictionary = new DictionaryController();
    public static GamesController games;

    static {
        try {
            games = new GamesController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static TranslatorController translator = new TranslatorController();

    public AppController() throws IOException {
        //data = new TrieDictionary();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sideBar = new SideBar();
        SideBarContainer.getChildren().add(sideBar);
        mainscreen = MainScreen;
    }


//    public void LoadGames(ActionEvent event) throws IOException {
//        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        fxmlLoader = new FXMLLoader(App.class.getResource("Games/Games.fxml"));
//        scene = new Scene(fxmlLoader.load());
//        stage.setTitle("Games");
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    public void LoadDictionary(ActionEvent event) throws IOException {
//        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        fxmlLoader = new FXMLLoader(App.class.getResource("Dictionary/Dictionary.fxml"));
//        scene = new Scene(fxmlLoader.load());
//        stage.setTitle("Dictionary");
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    public void LoadTranslator(ActionEvent event) throws IOException {
//        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        fxmlLoader = new FXMLLoader(App.class.getResource("Translator.fxml"));
//        scene = new Scene(fxmlLoader.load());
//        stage.setTitle("Translator");
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    @FXML
//    public void about(Event event) {
//        Alert info = new Alert(Alert.AlertType.INFORMATION);
//        info.setTitle("About us");
//        info.setContentText("This project is made for education purpose, by HHD group in UET university");
//        info.show();
//    }


}
