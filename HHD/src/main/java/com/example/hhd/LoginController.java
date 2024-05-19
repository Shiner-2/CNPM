package com.example.hhd;

import com.example.hhd.Database.UserManager;
import com.example.hhd.Dictionary.DictionaryController;
import javafx.beans.binding.Bindings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Dictionary;
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
    public void onLoginClick() throws IOException {
        String username = Username.getText();
        String password = Password.getText();
        if(UserManager.isValidCredentials(username,password)) {
            PublicValue.username = username;
            PublicValue.profile = UserManager.getProfile(username);
            PublicValue.user = UserManager.getUser(username);

            if (PublicValue.profile[1] != null) {
                PublicValue.dataVI_EN.loadRecentWord(PublicValue.profile[1]);
            }
            if (PublicValue.profile[2] != null) {
                PublicValue.dataEN_VI.loadRecentWord(PublicValue.profile[2]);
            }

            Stage newWindow = new Stage();
            newWindow.setTitle("WordLish");
            FXMLLoader loader = new FXMLLoader(App.class.getResource("App.fxml"));
            newWindow.setScene(new Scene(loader.load()));
            newWindow.show();

            Stage stage = (Stage) container.getScene().getWindow();
            stage.close();
        }else{
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setContentText("Wrong username or password");
            a.show();
        }
    }

    public void onSignUpBtnClick() throws IOException {
        Stage newWindow = new Stage();
        newWindow.setTitle("SignUp");
        FXMLLoader loader = new FXMLLoader(App.class.getResource("SignUp.fxml"));
        newWindow.setScene(new Scene(loader.load()));
        newWindow.show();

        Stage stage = (Stage) container.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shownPassword.setVisible(false);
        container.setOnMouseClicked(event -> {
            container.requestFocus();
        });
    }
}
