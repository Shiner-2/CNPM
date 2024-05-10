package com.example.hhd;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class AddWordViewController {
    @FXML
    private Button AddWordSubmitBtn;
    @FXML
    private TextArea UserWordEntered;
    @FXML
    private TextArea UserDefinationEntered;

    Dictionary data = new TrieDictionary();

    public AddWordViewController() throws IOException {
    }

    @FXML
    public void onAddWordSubmit(Event event) {
        String word = UserWordEntered.getText();
        word = word.toLowerCase();
        String defination = UserDefinationEntered.getText();
        if(defination==null){
            defination = "";
        }
        if(word==null){
            return;
        }
        Word w = new Word(word,defination);
        String tmp = "@"+w.getWord()+"\n"+w.getDefinition();
        w.setDefinition(tmp);
        if(!data.contains(word)){
            data.insertAndSave(w);
            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setTitle("Success");
            success.setContentText("Word have been added");
            success.show();
        }else{
            Alert fail = new Alert(Alert.AlertType.ERROR);
            fail.setTitle("Error");
            fail.setContentText("This word is currently in the dictionary. You need to Edit it in different scene");
            fail.show();
        }
    }


}
