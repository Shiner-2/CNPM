package com.example.hhd;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class TranslatorController {
    @FXML
    private Label lbLeft;
    @FXML
    private Label lbRight;
    @FXML
    private TextArea TFLeft;
    @FXML
    private TextArea TFRight;
    private boolean state = true;

    public void changeLanguage(Event event) {
        state = !state;
        String s = lbLeft.getText();
        String ss = lbRight.getText();
        lbLeft.setText(ss);
        lbRight.setText(s);
        s = TFLeft.getText();
        ss = TFRight.getText();
        TFLeft.setText(s);
        TFRight.setText(ss);
    }

    public void translate(Event event) throws IOException {
        String input = TFLeft.getText();
        if(state==true) {
            TFRight.setText(APITranslator.translate("vi", "en", input));
        } else{
            TFRight.setText(APITranslator.translate("en", "vi", input));
        }
    }

    public void LoadApp(Event event) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("App.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("HHD Dictionary");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void speakLeft(Event event) {
        if(state==true) {
            TTS.playSoundGoogleTranslateViToEn(TFLeft.getText());
        }else{
            TTS.playSoundGoogleTranslateEnToVi(TFLeft.getText());
        }
    }

    @FXML
    public void speakRight(Event event) {
        if(state==false) {
            TTS.playSoundGoogleTranslateViToEn(TFRight.getText());
        }else{
            TTS.playSoundGoogleTranslateEnToVi(TFRight.getText());
        }
    }
}
