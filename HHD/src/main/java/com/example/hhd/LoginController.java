package com.example.hhd;

import com.example.hhd.Database.UserManager;
import com.example.hhd.Dictionary.DictionaryController;
import javafx.beans.binding.Bindings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField Username;
    @FXML
    private PasswordField Password;
    @FXML
    private TextField shownPassword;
    @FXML
    private CheckBox showPassword;
    @FXML
    private AnchorPane container;

    @FXML
    public void CheckboxChecked(Event e) {
        if(showPassword.isSelected()) {
            shownPassword.setText(Password.getText());
            shownPassword.setVisible(true);
            Password.setVisible(false);
        } else{
            Password.setText(shownPassword.getText());
            shownPassword.setVisible(false);
            Password.setVisible(true);
        }
    }

    @FXML
    public void onLoginClick(Event e) {
        String username = Username.getText();
        String password = Password.getText();
//        UserManager.getUserByUsername(username);
        DictionaryController.dataVI_EN.loadRecentWord(UserManager.getProfile(username)[1]);
        DictionaryController.dataEN_VI.loadRecentWord(UserManager.getProfile(username)[2]);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shownPassword.setVisible(false);
        container.setOnMouseClicked(event -> {
            container.requestFocus();
        });
    }
}
