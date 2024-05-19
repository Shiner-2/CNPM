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

import java.io.FileNotFoundException;
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
            data = new TrieDictionary(TrieDictionary.EN_VI, true);
            // data = new TrieDictionary(TrieDictionary.VI_EN, true);
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

    public static HomeController home = new HomeController();

    public static TranslatorController translator = new TranslatorController();

    public static UserController user;

    static {
        try {
            user = new UserController();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public AppController() throws IOException {
        //data = new TrieDictionary();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sideBar = new SideBar();
        SideBarContainer.getChildren().add(sideBar);
        mainscreen = MainScreen;
        mainscreen.getChildren().clear();
        mainscreen.getChildren().add(home);
    }



}
