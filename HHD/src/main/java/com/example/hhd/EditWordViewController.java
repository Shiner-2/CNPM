package com.example.hhd;


import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class EditWordViewController implements Initializable {
    @FXML
    private TextArea UserWordEnteredEdit;
    @FXML
    private TextArea UserDefinationEnteredEdit;

    Dictionary data = new TrieDictionary();
    String word = "";
    Word ww = new Word("k","k");

    public EditWordViewController() throws IOException {
        File myObj = new File("HHD/src/main/resources/data/temp.txt");
        Scanner myReader = new Scanner(myObj);
        word = myReader.nextLine();
        word = word.toLowerCase();
        Word w = new Word("hello","bye");

        ArrayList<Word> arr = new ArrayList<>();
        arr = data.search(word);

        for(int i = 0; i < arr.size(); i++){
            if(arr.get(i).getWord().equals(word)){
                w = arr.get(i);
                break;
            }
        }

        ww = w;
    }

    @FXML
    public void onSubmitEdit(Event event) {
        Word nw = new Word("","");
        nw.setWord(UserWordEnteredEdit.getText());
        nw.setDefinition(UserDefinationEnteredEdit.getText());
        data.deleteAndSave(nw);
        data.insertAndSave(nw);
        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setTitle("Success");
        success.setContentText("Word have been edited");
        success.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserWordEnteredEdit.setText(ww.getWord());
        UserWordEnteredEdit.setDisable(true);
        UserDefinationEnteredEdit.setText(ww.getDefinition());
    }
}
