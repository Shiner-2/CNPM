module com.example.hhd {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    opens com.example.hhd to javafx.fxml;
    exports com.example.hhd;
    exports com.example.hhd.Games.Wordle;
    opens com.example.hhd.Games.Wordle to javafx.fxml;
    exports com.example.hhd.Games.Hangman;
    opens com.example.hhd.Games.Hangman to javafx.fxml;
    exports com.example.hhd.Games;
    opens com.example.hhd.Games to javafx.fxml;
    exports com.example.hhd.Games.Quiz;
    opens com.example.hhd.Games.Quiz to javafx.fxml;
    exports com.example.hhd.Games.Scrabble;
    opens com.example.hhd.Games.Scrabble to javafx.fxml;
    exports com.example.hhd.Games.WordPicture;
    opens com.example.hhd.Games.WordPicture to javafx.fxml;
    exports com.example.hhd.Dictionary;
    opens com.example.hhd.Dictionary to javafx.fxml;
    exports com.example.hhd.TTS;
    opens com.example.hhd.TTS to javafx.fxml;
    exports com.example.hhd.Translator;
    opens com.example.hhd.Translator to javafx.fxml;
    exports com.example.hhd.Algo;
    opens com.example.hhd.Algo to javafx.fxml;
    exports com.example.hhd.SideBar;
    opens com.example.hhd.SideBar to javafx.fxml;
    exports com.example.hhd.Database;
    requires javafx.web;
    requires jlayer;
    requires java.sql;
}