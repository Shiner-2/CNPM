package com.example.hhd.Games.Wordle;

import com.example.hhd.App;
import com.example.hhd.AppController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class WordleWordController extends VBox implements Initializable {
    @FXML
    private HBox WordleWordContainer;
    private Integer sz = 5;
    private ArrayList<WordleLetterController> word = new ArrayList<>();
    private String cur = "";
    private String HiddenWord = "DOGGY";

    public void setHiddenWord(String hiddenWord) {
        HiddenWord = hiddenWord;
    }

    public String getCur() {
        return cur;
    }

    public HBox getWordleWordContainer() {
        return WordleWordContainer;
    }

    public WordleWordController() {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(
                "Games/Wordle/Wordle-word.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void checkWord() {
        int cnt = 0;
        String s = cur.toLowerCase();
        HiddenWord = HiddenWord.toLowerCase();
        HashMap<Character,Integer> mp = new HashMap<>();
        for(int i = 0 ; i < sz; i++) {
            if (s.charAt(i) == HiddenWord.charAt(i)) continue;
            Character c = HiddenWord.charAt(i);
            if (mp.containsKey(c)) {
                Integer cur = mp.get(c);
                mp.put(c,cur+1);
            } else{
                mp.put(c,1);
            }
        }
        for(int i = 0 ; i < sz; i++) {
            char User = s.charAt(i);
            char Hidden = HiddenWord.charAt(i);
            WordleLetterController lt = word.get(i);
            lt.setLetter(String.valueOf(s.charAt(i)));
            if(s.charAt(i) == HiddenWord.charAt(i)) {
                cnt++;
                lt.setCorrect();
            } else{
                if (mp.containsKey(User) && mp.get(User) > 0) {
                    Integer cur = mp.get(User);
                    mp.put(User,cur-1);
                    lt.setHas();
                } else {
                    lt.setWrong();
                }
            }
        }

        if (cnt == 5) {
            WordleController.EndGame(true);
        }
    }

    public void setWord(String s) {
        s = s.toUpperCase();
        if(s.length()>5 || s == null) {
            System.out.println("something gone wrong");
            return;
        }
        for(int i = 0; i < s.length(); i++) {
            word.get(i).setLetter(String.valueOf(s.charAt(i)));
        }
        for(int i = s.length(); i < 5; i++) {
            word.get(i).setLetter(" ");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(int i = 0 ; i < sz; i++) {
            WordleLetterController lt = new WordleLetterController();
            lt.setLetter(" ");
            word.add(lt);
            WordleWordContainer.getChildren().add(lt);
        }

        WordleWordContainer.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().toString().equals("BACK_SPACE")) {
                if(cur.length()>0) {
                    cur = cur.substring(0, cur.length() - 1);
                }
            }else {
                if(keyEvent.getCode().toString().toUpperCase().length()>1){
                    if(keyEvent.getCode().toString().equals("ENTER")) {
                        if(cur.length() == sz && AppController.data.contains(cur)) {
                            checkWord();
                            keyEvent.consume();
                            WordleController.nextWord();
                            return;
                        } else{
                            if (cur.length() != sz) {
                                Alert a = new Alert(Alert.AlertType.INFORMATION);
                                a.setTitle("Wrong play");
                                a.setContentText("You must enter 5 letters");
                                a.show();

                                a.setOnCloseRequest(dialogEvent -> {
                                    WordleController.onLoad();
                                });
                            } else{
                                Alert a = new Alert(Alert.AlertType.INFORMATION);
                                a.setTitle("Invalid Word");
                                a.setContentText("You must enter a word in dictionary");
                                a.show();

                                a.setOnCloseRequest(dialogEvent -> {
                                    WordleController.onLoad();
                                });
                            }
                        }
                    }
                    keyEvent.consume();
                    WordleWordContainer.requestFocus();
                    return;
                }
                char c = keyEvent.getCode().toString().toUpperCase().charAt(0);
                if(c >= 'A'&& c <= 'Z' && cur.length() < 5) {
                    cur += keyEvent.getText().toUpperCase();
                }
            }
            setWord(cur);
            cur = cur.toLowerCase();
        });
    }


}
