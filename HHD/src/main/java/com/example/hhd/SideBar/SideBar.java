package com.example.hhd.SideBar;

import com.example.hhd.App;
import com.example.hhd.AppController;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SideBar extends AnchorPane implements Initializable {
    @FXML
    private VBox header;
    @FXML
    private VBox body;
    @FXML
    private VBox footer;
    @FXML
    private AnchorPane container;
    @FXML
    private Line line;
    @FXML
    private Line line1;

    public static SideBarItem curSelect = null;

    private int bigsz = 240;
    private int smallsz = 80;

    public SideBar() {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("UI/SideBar/SideBar.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        //setNoHover();
    }

    public void setNoHover() {
        container.setPrefWidth(smallsz);
        header.setPrefWidth(smallsz);
        body.setPrefWidth(smallsz);
        footer.setPrefWidth(smallsz);
        line.setVisible(true);
        //line1.setVisible(false);

        container.getChildren().remove(line1);

        for(Node node: header.getChildren()) {
            if(node instanceof SideBarItem) {
                ((SideBarItem) node).SideBarNoHover();
            }
        }
        for(Node node: body.getChildren()) {
            if(node instanceof SideBarItem) {
                ((SideBarItem) node).SideBarNoHover();
            }
        }
        for(Node node: footer.getChildren()) {
            if(node instanceof SideBarItem) {
                ((SideBarItem) node).SideBarNoHover();
            }
        }
    }

    public void setHover() {
        container.setPrefWidth(bigsz);
        header.setPrefWidth(bigsz);
        body.setPrefWidth(bigsz);
        footer.setPrefWidth(bigsz);
        line.setVisible(false);
        line1.setVisible(true);

        container.getChildren().add(line1);

        for(Node node: header.getChildren()) {
            if(node instanceof SideBarItem) {
                ((SideBarItem) node).SideBarHover();
            }
        }
        for(Node node: body.getChildren()) {
            if(node instanceof SideBarItem) {
                ((SideBarItem) node).SideBarHover();
            }
        }
        for(Node node: footer.getChildren()) {
            if(node instanceof SideBarItem) {
                ((SideBarItem) node).SideBarHover();
            }
        }
    }

    public static void loadDictionary() {
        AppController.mainscreen.getChildren().clear();
        AppController.mainscreen.getChildren().add(AppController.dictionary);
    }

    public static void loadGames() {
        AppController.mainscreen.getChildren().clear();
        AppController.mainscreen.getChildren().add(AppController.games);
    }

    public static void loadTranslator() {
        AppController.mainscreen.getChildren().clear();
        AppController.mainscreen.getChildren().add(AppController.translator);
    }

    public static void loadHome() {
        AppController.mainscreen.getChildren().clear();
        AppController.mainscreen.getChildren().add(AppController.home);
        AppController.home.updateRecentWord();
    }

    public static void loadUser() {
        AppController.mainscreen.getChildren().clear();
        AppController.mainscreen.getChildren().add(AppController.user);
        AppController.user.updateStat();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        SideBarItem head1 = new SideBarItem("HHD/src/main/resources/img/UI_button/home.png",
                "HHD/src/main/resources/img/UI_button/home1.png", 80,70,"home");
        header.getChildren().add(head1);
        head1.setSelected(true);
        head1.setHover();
        head1.setOnMouseClicked(event -> {
            loadHome();
            head1.onClick();
        });
        curSelect = head1;

        SideBarItem body1 = new SideBarItem("HHD/src/main/resources/img/UI_button/book.png",
                "HHD/src/main/resources/img/UI_button/book1.png", 77, 70, "dictionary");
        body1.setOnMouseClicked(event -> {
            loadDictionary();
            body1.onClick();
        });
        SideBarItem body2 = new SideBarItem("HHD/src/main/resources/img/UI_button/game.png",
                "HHD/src/main/resources/img/UI_button/game1.png", 83, 50, "game");
        body2.setOnMouseClicked(event -> {
            loadGames();
            body2.onClick();
        });
        SideBarItem body3= new SideBarItem("HHD/src/main/resources/img/UI_button/translator.png",
                "HHD/src/main/resources/img/UI_button/translator1.png", 83, 50, "translator");
        body3.setOnMouseClicked(event -> {
            loadTranslator();
            body3.onClick();
        });

        body.getChildren().addAll(body1,body2,body3);

        SideBarItem foot = new SideBarItem("HHD/src/main/resources/img/UI_button/user.png",
                "HHD/src/main/resources/img/UI_button/user1.png", 70,70,"User");

        foot.setOnMouseClicked(event -> {
            loadUser();
            foot.onClick();
        });

        footer.getChildren().addAll(foot);


        container.setOnMouseEntered(event -> {
            setHover();
        });

        container.setOnMouseExited(event -> {
            setNoHover();
        });

        setNoHover();
        // TODO: set onclick
    }

}
