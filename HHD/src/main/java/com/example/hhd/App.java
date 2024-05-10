package com.example.hhd;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("App.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("HHD");
        stage.setScene(scene);
        stage.show();
    }

    // nothing
    public static void main(String[] args) {
        launch();
    }
}
