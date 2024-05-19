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

    static Dictionary dataVI_EN = PublicValue.dataVI_EN;
    static Dictionary dataEN_VI = PublicValue.dataEN_VI;

    private int mode = Dictionary.EN_VI;

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
    private void onSwitch() {
        mode ^= 1;
        if (mode == Dictionary.EN_VI) {
            data = dataEN_VI;
            wordDefinition.getChildren().clear();
            userInputWord.setText("");
            recommendWord.getItems().clear();
        } else {
            data = dataVI_EN;
            wordDefinition.getChildren().clear();
            userInputWord.setText("");
            recommendWord.getItems().clear();
        }
    }

    public void setMode(int mode) {
        this.mode = mode;
        if (mode == Dictionary.EN_VI) {
            data = dataEN_VI;
            wordDefinition.getChildren().clear();
            userInputWord.setText("");
            recommendWord.getItems().clear();
        } else {
            data = dataVI_EN;
            wordDefinition.getChildren().clear();
            userInputWord.setText("");
            recommendWord.getItems().clear();
        }
    }

    @FXML
    private void onUserTyping(){
        String inputText = userInputWord.getText();
        inputText = inputText.toLowerCase(Locale.ROOT);

        setRecommendWord(inputText);
    }

    public void setRecommendWord(String inputText) {
        recommendWord.getItems().clear();
        recommendWord.getItems().addAll(data.searchWithRecentWord(inputText));
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
            data.addRecentWord(curString);
        });
    }

    // TODO: change this
    public void LoadApp(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("App.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        AppController.home.updateRecentWord();

        stage.setTitle("HHD");
        stage.setScene(scene);
        stage.show();
    }

    public void speak(Event event) {
        TTS.playSoundGoogleTranslateEnToVi(currentWord.getWord());
    }

    private void showWord(String word) {
        Text tx = new Text("\t"+word + "\n");
        tx.setStyle("-fx-font: 18px \"arial,sans-serif\";");
        wordDefinition.getChildren().add(tx);
    }

    private void showPronunciation(String pronunciation) {
        Text tx = new Text("\t"+pronunciation + "\n");
        tx.setStyle("-fx-font: 14px \"arial,sans-serif\";");
        wordDefinition.getChildren().add(tx);
    }

    private void showType(String type) {
        Text tx = new Text("\t" + type + "\n");
        tx.setStyle("-fx-font: italic 14px \"arial,sans-serif\";");
        wordDefinition.getChildren().add(tx);
    }

    private void showMeaning(String meaning, int index) {
        Text tx = new Text("\t   " + index + ". " + meaning + "\n");
        tx.setStyle("-fx-font: 13px \"arial,sans-serif\";");
        wordDefinition.getChildren().add(tx);
    }

    private void showExample(String example, String meaning) {
        if (meaning.isEmpty()) {
            Text tx = new Text("\t       ex. " + example + "\n");
            tx.setStyle("-fx-font: 12px \"arial,sans-serif\";");
            wordDefinition.getChildren().add(tx);
        } else {
            Text tx = new Text("\t       ex. " + example + " : " + meaning + "\n");
            tx.setStyle("-fx-font: 12px \"arial,sans-serif\";");
            wordDefinition.getChildren().add(tx);
        }
    }

    private void showPhrase(String phrase) {
        Text tx = new Text("\tidioms: " + phrase + "\n");
        tx.setStyle("-fx-font: 14px \"arial,sans-serif\";");
        wordDefinition.getChildren().add(tx);
    }

    public void showWordDefinition(Word Eword) {
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
                    showMeaning(line.substring(1).strip(), ++ countMeaning);
                    break;
                case '=': // example
                    int separator = line.indexOf('+');
                    if (separator < 0) {
                        String example = line.substring(1).strip();
                        showExample(example, "");
                    } else {
                        String example = line.substring(1, separator).strip();
                        String meaning = line.substring(separator + 1).strip();
                        showExample(example, meaning);
                    }
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