package com.example.hhd.Dictionary;

import com.example.hhd.*;
import com.example.hhd.Algo.Dictionary;
import com.example.hhd.Algo.TrieDictionary;
import com.example.hhd.Algo.Word;
import com.example.hhd.TTS.TTS;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class DictionaryController extends AnchorPane {

    @FXML
    private Button AddWordBtn;

    Dictionary data = AppController.data;

    @FXML
    private TextField userInputWord;

    @FXML
    private ListView<Word> recommendWord;

    @FXML
    private TextFlow wordDefinition;

    private Word currentWord;

    public static String curString = "";

    public DictionaryController() {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(
                "Dictionary/Dictionary.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private void onUserTyping(){
        String inputText = userInputWord.getText();
        inputText = inputText.toLowerCase(Locale.ROOT);

        recommendWord.getItems().clear();
        recommendWord.getItems().addAll(data.search(inputText));
        recommendWord.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Word item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getWord() == null) {
                    setText(null);
                } else {
                    setText(item.getWord());
                }
            }
        });
        recommendWord.setOnMouseClicked(mouseEvent -> {
            currentWord = recommendWord.getSelectionModel().getSelectedItem();
            curString = currentWord.getWord();
            wordDefinition.getChildren().clear();
            showWordDefinition(currentWord);
        });
    }

    
    // TODO: change this
    public void LoadApp(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("App.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("HHD");
        stage.setScene(scene);
        stage.show();
    }

    public void speak(Event event) {
        TTS.playSoundGoogleTranslateEnToVi(currentWord.getWord());
    }

    private void showWord(String word) {
        //System.out.println(word); // bold + header font

        Text tx = new Text("\t"+word + "\n");
        tx.setStyle("-fx-font: 20px \"arial,sans-serif\";");
        wordDefinition.getChildren().add(tx);
    }

    private void showPronunciation(String pronunciation) {
        //System.out.println(pronunciation); // sound button

        Text tx = new Text("\t"+pronunciation + "\n");
        tx.setStyle("-fx-font: 14px \"arial,sans-serif\";");
        wordDefinition.getChildren().add(tx);

    }

    private void showType(String type) {
        //System.out.println(type); // italic type

        Text tx = new Text("\t"+type + "\n");
        tx.setStyle("-fx-font: italic 14px \"arial,sans-serif\";");
        wordDefinition.getChildren().add(tx);
    }

    private void showMeaning(String meaning, int index) {
        //System.out.println("\t" + index + ". " + meaning);

        Text tx = new Text("\t\t" + index + ". " + meaning + "\n");
        tx.setStyle("-fx-font: 14px \"arial,sans-serif\";");
        wordDefinition.getChildren().add(tx);
    }

    private void showExample(String example, String meaning) {
        //System.out.println("\t\tex. " + example + " : " + meaning);

        Text tx = new Text("\t\t\tex. " + example + " : " + meaning + "\n");
        tx.setStyle("-fx-font: 12px \"arial,sans-serif\";");
        wordDefinition.getChildren().add(tx);
    }

    private void showPhrase(String phrase) {
        //System.out.println("phrase: " + phrase);

        Text tx = new Text("\tphrase: " + phrase + "\n");
        tx.setStyle("-fx-font: 14px \"arial,sans-serif\";");
        wordDefinition.getChildren().add(tx);
    }

    private void showWordDefinition(Word Eword) {
        if (Eword == null) return ;
        String word = Eword.getWord(), definition = Eword.getDefinition();

        List<String> lines = new ArrayList<>(List.of(definition.split("\n")));

        showWord(word);

        for (int i = 0, countMeaning = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            switch (line.charAt(0)) {
                case '@': // word
                    if (line.charAt(line.length() - 1) == '/') {
                        // pronunciation
                        showPronunciation(line.substring(line.indexOf('/')));
                    }
                    break;
                case '!':
                    countMeaning = 0;
                    showPhrase(line.substring(1).strip());
                    break;
                case '*': // type
                    countMeaning = 0; // number of meaning in same type
                    showType(line.substring(1).strip());
                    break;
                case '-': // meaning
                    if (line.charAt(1) == ' ') {
                        showMeaning(line.substring(1).strip(), ++ countMeaning);
                    }
                    // else malformed input
                    break;
                case '=': // example
                    int separator = line.indexOf('+');
                    String example = line.substring(1, separator);
                    String meaning = line.substring(separator + 1);
                    showExample(example, meaning);
                    break;
                default:
//                    malformed input
//                    throw new RuntimeException("can't read word definition, wrong format!");
            }
        }
    }

    @FXML
    public void onAddWord(Event event) throws IOException {
        Stage newWindow = new Stage();
        newWindow.setTitle("Add word");
        FXMLLoader loader = new FXMLLoader(App.class.getResource("Dictionary/AddWordView.fxml"));
        newWindow.setScene(new Scene(loader.load()));
        newWindow.show();
//        newWindow.setOnCloseRequest(windowEvent -> {
//            //System.out.println("close");
//            try {
//                data = new TrieDictionary();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        });
    }

    @FXML
    public void onDeleteWord(Event event) {
        if(currentWord == null) {
            return;
        }
        if(data.contains(currentWord.getWord())){
            data.deleteAndSave(currentWord);
            wordDefinition.getChildren().clear();
            recommendWord.getItems().clear();
        }else{
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setContentText("This word is not in the dictionary");
            error.show();
        }
    }

    @FXML
    public void onEditWord(Event event) throws IOException {
        if(currentWord == null) {
            return;
        }
        if(data.contains(currentWord.getWord())) {
            Stage newWindow = new Stage();
            newWindow.setTitle("Edit Word");
            FXMLLoader loader = new FXMLLoader(App.class.getResource("Dictionary/EditWordView.fxml"));
            newWindow.setScene(new Scene(loader.load()));
            newWindow.show();
//            newWindow.setOnCloseRequest(windowEvent -> {
//                //System.out.println("close");
//                try {
//                    data = new TrieDictionary();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            });
            wordDefinition.getChildren().clear();
            recommendWord.getItems().clear();
        }
    }


}