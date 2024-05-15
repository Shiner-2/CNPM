package com.example.hhd;

import com.example.hhd.Database.UserManager;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField Username;
    @FXML
    private TextField Password;
    @FXML
    private CheckBox showPassword;

    private String password = "";


    // TODO: watch video tutorial on this
    @FXML
    public void onPasswordType(KeyEvent e) {

    }

    @FXML
    public void onLoginClick(Event e) {
        String username = Username.getText();
        password = Password.getText();
        UserManager.getUserByUsername(username);
    }

    public LoginController() {
        System.out.println("hello");
        UserManager.getUserByUsername("hello");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
