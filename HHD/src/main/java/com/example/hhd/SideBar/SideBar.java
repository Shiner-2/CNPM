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
        for(Node node: header.getChildren()) {
            if(node instanceof SideBarItem) {
                ((SideBarItem) node).setNoHover();
            }
        }
        for(Node node: body.getChildren()) {
            if(node instanceof SideBarItem) {
                ((SideBarItem) node).setNoHover();
            }
        }
        for(Node node: footer.getChildren()) {
            if(node instanceof SideBarItem) {
                ((SideBarItem) node).setNoHover();
            }
        }
    }

    public void setHover() {
        container.setPrefWidth(bigsz);
        header.setPrefWidth(bigsz);
        body.setPrefWidth(bigsz);
        footer.setPrefWidth(bigsz);
        for(Node node: header.getChildren()) {
            if(node instanceof SideBarItem) {
                ((SideBarItem) node).setHover();
            }
        }
        for(Node node: body.getChildren()) {
            if(node instanceof SideBarItem) {
                ((SideBarItem) node).setHover();
            }
        }
        for(Node node: footer.getChildren()) {
            if(node instanceof SideBarItem) {
                ((SideBarItem) node).setHover();
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String path = "HHD/src/main/resources/Scrabble/Image/PNG/Blue/letter.png";
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Image image = new Image(fis,70,70,false,false);

        SideBarItem head1 = new SideBarItem();
        head1.setImg(image);
        head1.setName("logo");
        header.getChildren().add(head1);

        SideBarItem body1 = new SideBarItem();
        SideBarItem body2 = new SideBarItem();
        SideBarItem body3 = new SideBarItem();

        path = "HHD/src/main/resources/img/UI button/home.png";
        try {
            fis = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        body1.setImg(image);

        body1.setOnMouseEntered(event -> {
            try {
                FileInputStream f = new FileInputStream("HHD/src/main/resources/img/UI button/home.png");
                Image image1 = new Image(f);
                body1.setImg(image1);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        body1.setOnMouseExited(event -> {
            try {
                FileInputStream f = new FileInputStream("HHD/src/main/resources/img/UI button/home1.png");
                Image image1 = new Image(f);
                body1.setImg(image1);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        body2.setImg(image);
        body3.setImg(image);
        body1.setName("dictionary");
        body2.setName("Games");
        body3.setName("Translator");

        body1.setOnMouseClicked(event -> {
            loadDictionary();
        });

        body2.setOnMouseClicked(event -> {
            loadGames();
        });

        body3.setOnMouseClicked(event -> {
            loadTranslator();
        });

        body.getChildren().addAll(body1,body2,body3);

        SideBarItem foot1 = new SideBarItem();
        foot1.setImg(image);
        foot1.setName("Sign out");
        footer.getChildren().add(foot1);

        setNoHover();

//        TranslateTransition translateTransition = new TranslateTransition();
//        translateTransition.setDuration(Duration.seconds(1));
//        translateTransition.setNode(container);
//        translateTransition.setToX(100);
//        translateTransition.setToY(0);
//
//
//        TranslateTransition translateTransition1 = new TranslateTransition();
//        translateTransition.setDuration(Duration.seconds(1));
//        translateTransition.setNode(container);
//        translateTransition.setToX(0);
//        translateTransition.setToY(0);
//
//        ScaleTransition transition = new ScaleTransition();
//        transition.setDuration(Duration.seconds(1));
//        transition.setToX(3);
//        transition.setNode(container);
//
//        ScaleTransition transition1 = new ScaleTransition();
//        transition1.setDuration(Duration.seconds(1));
//        transition1.setToX(1);
//        transition1.setNode(container);

        container.setOnMouseEntered(event -> {
            setHover();
//            translateTransition.play();
//            transition.play();
        });

        container.setOnMouseExited(event -> {
            setNoHover();
//            transition1.play();
//            translateTransition1.play();
        });

        // TODO: set onclick
    }

}
