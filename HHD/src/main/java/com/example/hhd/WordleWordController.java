package com.example.hhd;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.HashMap;

public class WordleWordController extends VBox {
    @FXML
    private HBox WordleWordContainer;
    private Integer sz = 5;
    //private TrieDictionary trie = new TrieDictionary();

    public WordleWordController(Integer sz) {
        this.sz = sz;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "Wordle-word.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    public void setWord(String s, String HiddenWord) {
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
            WordleLetterController lt = new WordleLetterController();
            lt.setLetter(String.valueOf(s.charAt(i)));
            if(s.charAt(i) == HiddenWord.charAt(i)) {
                lt.setStyle("-fx-background-color: green;");
            } else{
                if (mp.containsKey(User) && mp.get(User) > 0) {
                    Integer cur = mp.get(User);
                    mp.put(User,cur-1);
                    lt.setStyle("-fx-background-color: yellow;");
                } else {
                    lt.setStyle("-fx-background-color: red;");
                }
            }
            WordleWordContainer.getChildren().add(lt);
        }
    }
}
