package com.example.hhd;

import com.example.hhd.Database.GameManagement;
import com.example.hhd.Database.UserManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class SignUpController{
    @FXML
    private PasswordField Password;
    @FXML
    private PasswordField Password1;
    @FXML
    private TextField Username;
    @FXML
    private TextField DisplayName;

    @FXML
    public void SignUp() {
        if(Username.getText() == null || Username.getText().length() < 5 || Username.getText().length() > 16) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setContentText("Username must be 5-16 character");
            a.show();
            return;
        }
        if(Password.getText() == null || Password.getText().length() < 5 || Password.getText().length() > 16) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setContentText("Pass must be 5-16 character");
            a.show();
            return;
        }
        if(!Password.getText().equals(Password1.getText())) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setContentText("Your password is not identical");
            a.show();
            return;
        }
        if(UserManager.getUser(Username.getText()) != null) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setContentText("This username is already taken");
            a.show();
            return;
        }
        UserManager.addUser(Username.getText(), Password.getText(), DisplayName.getText());
        String id = UserManager.getUser(Username.getText())[0];
        for(int i = 1; i <= 5; i++) {
            GameManagement.addUserGame(Integer.parseInt(id),i,"",0,0);
        }
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Success");
        a.setContentText("Your account was successfully created, close this message to Login");
        a.setOnCloseRequest(dialogEvent -> {
            try {
                goLogIn();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        a.show();
    }

    public void goLogIn() throws IOException {
        Stage newWindow = new Stage();
        newWindow.setTitle("Login");
        FXMLLoader loader = new FXMLLoader(App.class.getResource("Login.fxml"));
        newWindow.setScene(new Scene(loader.load()));
        newWindow.show();

        Stage stage = (Stage) Password.getScene().getWindow();
        stage.close();
    }
}
