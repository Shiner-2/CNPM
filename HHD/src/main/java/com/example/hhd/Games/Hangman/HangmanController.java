package com.example.hhd.Games.Hangman;

import com.example.hhd.App;
import com.example.hhd.Algo.Dictionary;
import com.example.hhd.AppController;
import com.example.hhd.Games.GamesController;
import com.example.hhd.SideBar.SideBar;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HangmanController extends AnchorPane implements Initializable {
    @FXML
    private Label HangmanFail;
    @FXML
    private Circle HangmanFail1;
    @FXML
    private Line HangmanFail2;
    @FXML
    private Line HangmanFail3;
    @FXML
    private Line HangmanFail4;
    @FXML
    private Line HangmanFail5;
    @FXML
    private Line HangmanFail6;
    @FXML
    private Line HangmanFail0;
    @FXML
    private HBox HangmanHiddenWordContainer;
    @FXML
    private Label HangmanFailCountLabel;
    @FXML
    private VBox HangmanKeyBoardContainer;
    @FXML
    private HBox HangmanKeyBoardContainer1;
    @FXML
    private HBox HangmanKeyBoardContainer2;
    @FXML
    private HBox HangmanKeyBoardContainer3;
    @FXML
    private ImageView imgV1;
    @FXML
    private ImageView imgV2;
    @FXML
    private ImageView imgV3;
    private Integer wrongcnt = -1;
    private Integer correctcnt = 0;
    private Integer hiddencnt = 1;

    private String HiddenWord = "NULL";
    private List<String> IdList = new ArrayList<>();
    private Dictionary data = AppController.data;
    private Popup popup = new Popup();

    public HangmanController() throws IOException {
        HiddenWord = data.randomWord(10).getWord();
        HiddenWord = HiddenWord.toUpperCase();

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Games/Hangman/Hangman.fxml"));
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

    public void Replay() {
        HiddenWord = data.randomWord(10).getWord();
        HiddenWord = HiddenWord.toUpperCase();
        wrongcnt = -1;
        correctcnt = 0;
        hiddencnt = 1;
        IdList.clear();

        HangmanFail0.setVisible(false);
        HangmanFail.setVisible(false);
        HangmanFail1.setVisible(false);
        HangmanFail2.setVisible(false);
        HangmanFail3.setVisible(false);
        HangmanFail4.setVisible(false);
        HangmanFail5.setVisible(false);
        HangmanFail6.setVisible(false);

        HangmanHiddenWordContainer.getChildren().clear();
        HangmanKeyBoardContainer1.getChildren().clear();
        HangmanKeyBoardContainer2.getChildren().clear();
        HangmanKeyBoardContainer3.getChildren().clear();

        for(int i = 0; i < HiddenWord.length(); i++) {
            String id = "HiddenWord" + (i+1);
            HangmanHiddenLetterController lt = new HangmanHiddenLetterController();
            lt.setId(id);
            lt.hiden = String.valueOf(HiddenWord.charAt(i));
            IdList.add(id);
            HangmanHiddenWordContainer.getChildren().add(lt);

            for(int j = i-1 ; j >= 0 ; j--) {
                if(HiddenWord.charAt(i)==HiddenWord.charAt(j)){
                    break;
                }
                if(j==0) {
                    hiddencnt++;
                }
            }
        }

        for(int i = 0; i < 9; i++) {
            char c = (char) ('A'+i);
            HangmanKeyBoardKeyController key = new HangmanKeyBoardKeyController();
            key.setKey(String.valueOf(c));
            key.setId("HangmanKey" + c);
            key.setOnMouseClicked(e->{
                if(check(c)) key.Correct();
                else key.Wrong();
                Guess(String.valueOf(c),e);
            });
            HangmanKeyBoardContainer1.getChildren().add(key);
        }
        for(int i = 9; i < 18; i++) {
            char c = (char) ('A'+i);
            HangmanKeyBoardKeyController key = new HangmanKeyBoardKeyController();
            key.setKey(String.valueOf(c));
            key.setId("HangmanKey" + c);
            key.setOnMouseClicked(e->{
                if(check(c)) key.Correct();
                else key.Wrong();
                Guess(String.valueOf(c),e);
            });
            HangmanKeyBoardContainer2.getChildren().add(key);
        }
        for(int i = 18; i < 26; i++) {
            char c = (char) ('A'+i);
            HangmanKeyBoardKeyController key = new HangmanKeyBoardKeyController();
            key.setKey(String.valueOf(c));
            key.setId("HangmanKey" + c);
            key.setOnMouseClicked(e->{
                if(check(c)) key.Correct();
                else key.Wrong();
                Guess(String.valueOf(c),e);
            });
            HangmanKeyBoardContainer3.getChildren().add(key);
        }

        HangmanFailCountLabel.setText("You have failed 0/8");

    }

    public void showInfo(Event event) {
        popup.show(HangmanHiddenWordContainer.getScene().getWindow()
                ,HangmanHiddenWordContainer.localToScreen(0,0).getX()
                ,HangmanHiddenWordContainer.localToScreen(0,0).getY());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GamesController.setHoverEffect(imgV1,imgV2,imgV3);
        Replay();
        AnchorPane anchorPane = new AnchorPane();
        try {
            HangmanInfoController info = new HangmanInfoController();
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

    private void UpdateFailCount() {
        HangmanFailCountLabel.setText(String.format("You have failed %d/8",wrongcnt+1));
        switch (wrongcnt) {
            case 0:
                HangmanFail0.setVisible(true);
                break;
            case 1:
                HangmanFail1.setVisible(true);
                break;
            case 2:
                HangmanFail2.setVisible(true);
                break;
            case 3:
                HangmanFail3.setVisible(true);
                break;
            case 4:
                HangmanFail4.setVisible(true);
                break;
            case 5:
                HangmanFail5.setVisible(true);
                break;
            case 6:
                HangmanFail6.setVisible(true);
                break;
            default:
                HangmanFail.setVisible(true);

        }
        if(wrongcnt+1>=8) {
            lose();
        }
    }

    private void lose() {
        Alert losing = new Alert(Alert.AlertType.INFORMATION);
        losing.setTitle("You lose");
        losing.setContentText("Better luck next time");
        losing.show();
        CloseGame();
    }

    private void win() {
        Alert winning = new Alert(Alert.AlertType.INFORMATION);
        winning.setTitle("You win");
        winning.setContentText("GG Well Played");
        winning.show();
        CloseGame();
    }

    private void CloseGame() {
        for(Node container: HangmanKeyBoardContainer.getChildren()) {
            if(container instanceof HBox) {
                for(Node node: ((HBox) container).getChildren()) {
                    node.setDisable(true);
                }
            }
        }
        if(correctcnt >= hiddencnt) return;
        int i = 0;
        for(Node node: HangmanHiddenWordContainer.getChildren()) {
            if(node instanceof HangmanHiddenLetterController) {
                if(((HangmanHiddenLetterController) node).getLetter().equals("")) {
                    ((HangmanHiddenLetterController) node).setLetter(String.valueOf(HiddenWord.charAt(i)));
                    ((HangmanHiddenLetterController) node).setNotGuess();
                }
                i++;
            }
        }
    }

    private boolean check(char c) {
        for(int i = 0 ; i < HiddenWord.length(); i++){
            if(c == HiddenWord.charAt(i)) {
                correctcnt++;
                if(correctcnt>=hiddencnt) win();
                return true;
            }
        }
        wrongcnt++;
        UpdateFailCount();
        return false;
    }

    public void Guess(String s, MouseEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        s = s.toUpperCase();
        for(int i = 0; i < HiddenWord.length(); i++) {
            String cur = String.valueOf(HiddenWord.charAt(i));
            if (cur.equals(s)) {
                for(Node node: HangmanHiddenWordContainer.getChildren()) {
                    if(node instanceof HangmanHiddenLetterController) {
                        if (node.getId().equals(IdList.get(i))) {
                            ((HangmanHiddenLetterController) node).setLetter(cur);
                        }
                    }
                }
            }
        }
    }
}
