package com.example.hhd.Games.Wordle;

import com.example.hhd.*;
import com.example.hhd.Algo.Dictionary;
import com.example.hhd.Database.GameManagement;
import com.example.hhd.Games.GamesController;
import com.example.hhd.SideBar.SideBar;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class WordleController extends AnchorPane implements Initializable {
    @FXML
    private VBox WordleContainer;
    @FXML
    private Integer sz = 5;
    public static String HiddenWord = "DOGGY";
    public static boolean GameState = false;
    public static Integer GuessCount = 0;
    private Dictionary data = AppController.data;
    public static ArrayList<WordleWordController> board = new ArrayList<>();
    private Popup popup = new Popup();
    static String[] getUserData = GameManagement.getUserGame(Integer.parseInt(PublicValue.user[0]),4);

    @FXML
    private ImageView imgV1;
    @FXML
    private ImageView imgV2;
    @FXML
    private ImageView imgV3;

    public WordleController() throws IOException {
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

    public static void EndGame(boolean win) {
        for(int i = GuessCount + 1 ; i < 6; i++) {
            board.get(i).setDisable(true);
        }
        if(win) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("You win");
            a.setContentText("WELL PLAYED");
            a.show();
            GameState = true;

            WordleController.updateData();
        } else{
            if(GameState) {
                return;
            }
            WordleController.updateData();
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("You lose");
            a.setContentText("The Hidden Word is: " + HiddenWord);
            a.show();
        }
    }

    public static void updateData() {
        int id = Integer.parseInt(getUserData[0]);
        int hs = Integer.parseInt(getUserData[2]);
        int tw = Integer.parseInt(getUserData[3]) + 1;
        if(tw == 1) {
            hs = GuessCount;
        } else{
            hs = Math.min(hs,(GuessCount));
        }
        getUserData[2] = String.valueOf(hs);
        getUserData[3] = String.valueOf(tw);
        GameManagement.updateUserGame(id,"",hs,tw);
    }

    public static void onLoad() {
        board.get(GuessCount).getWordleWordContainer().requestFocus();
    }

    public void LoadGuide() {
        popup.show(WordleContainer.getScene().getWindow()
                ,WordleContainer.localToScreen(-10,-10).getX()
                ,WordleContainer.localToScreen(-10, -10).getY());
    }

    public static void nextWord() {
        GuessCount++;
        if(GuessCount>=6) {
            EndGame(false);
            //System.out.println("lose");
            return;
        }
        board.get(GuessCount).getWordleWordContainer().requestFocus();
    }

    public void NewGame() {
        board.clear();
        WordleContainer.getChildren().clear();
        GuessCount = 0;
        HiddenWord = data.randomWord(5).getWord().toLowerCase();
        GameState = false;
        for(int i = 0; i < 6; i++) {
            WordleWordController Word = new WordleWordController();
            Word.setHiddenWord(HiddenWord);
            board.add(Word);
            WordleContainer.getChildren().add(Word);
        }
        onLoad();

        int id = Integer.parseInt(getUserData[0]);
        int hs = Integer.parseInt(getUserData[2]);
        int tw = Integer.parseInt(getUserData[3]);
        GameManagement.updateUserGame(id,"",hs,tw);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LoadData();

        GamesController.setHoverEffect(imgV1,imgV2,imgV3);

        AnchorPane anchorPane = new AnchorPane();
        try {
            WordleInfoController info = new WordleInfoController();
            anchorPane.getChildren().add(info);
            ImageView imageView = info.getImg();
            imageView.setOnMouseClicked(event -> {
                popup.hide();
            });
            popup.getContent().add(anchorPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /*
        Board 6 line
        HiddenWord
     */
    public static void WordleToData() {
        // TODO: loadData
        String s = "";
        for(int i = 0; i < 6; i++) {
            s = s + board.get(i).getCur() + "\n";
        }
        s = s + HiddenWord + "\n";
        //System.out.println(s);

        int id = Integer.parseInt(getUserData[0]);
        int hs = Integer.parseInt(getUserData[2]);
        int tw = Integer.parseInt(getUserData[3]);
        GameManagement.updateUserGame(id,s,hs,tw);

    }

    public void LoadData() {
        // TODO: getData
//        String s = "hello\n" +
//                "happy\n" +
//                "\n" +
//                "\n" +
//                "\n" +
//                "\n" +
//                "dense";
        if(getUserData == null){
            NewGame();
            return;
        }
        String s = getUserData[1];
        if(s.length()<2){
            NewGame();
            return;
        }
        String[] dat = s.split("\n");
        board.clear();
        WordleContainer.getChildren().clear();
        HiddenWord = dat[6];
        GameState = false;
        for(int i = 0; i < 6; i++) {
            WordleWordController Word = new WordleWordController();
            Word.setHiddenWord(HiddenWord);
            board.add(Word);
            WordleContainer.getChildren().add(Word);
        }

        for(int i = 0; i < 6 ; i++) {
            if(dat[i].length() == 5) {
                board.get(i).setCur(dat[i]);
                board.get(i).checkWord();
                WordleController.nextWord();
            }
        }

        onLoad();
    }

}
