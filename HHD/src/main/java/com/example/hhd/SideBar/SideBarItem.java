package com.example.hhd.SideBar;

import com.example.hhd.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SideBarItem extends HBox implements Initializable {
    @FXML
    private ImageView img;
    @FXML
    private Label name;
    @FXML
    private HBox container;

    private boolean isSelected = false;

    private String unselectPath = "";
    private String selectPath = "";
    private String ItemName = "";

    private Image unselectImg;
    private Image selectImg;

    private double imgW;
    private double imgH;

    private int smallsz = 80;
    private int bigsz = 240;
    public SideBarItem(String unselectImgPath, String selectImgPath, double imgW, double imgH, String FunctionName) {
        this.unselectPath = unselectImgPath;
        this.selectPath = selectImgPath;
        this.imgW = imgW;
        this.imgH = imgH;
        ItemName = FunctionName.toUpperCase();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("UI/SideBar/SideBarItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void setName(String s) {
        name.setText(s);
    }

    public void setImg(Image i){
        img.setImage(i);
    }

    public void setNoHover() {
        img.setImage(unselectImg);
        name.setTextFill(Paint.valueOf("#2e2e3a"));
    }

    public void setHover() {
        img.setImage(selectImg);
        name.setTextFill(Paint.valueOf("#3b8484"));
    }

    public void SideBarHover() {
        container.setPrefWidth(bigsz);
        name.setVisible(true);
    }

    public void SideBarNoHover() {
        container.setPrefWidth(smallsz);
        name.setVisible(false);
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void onClick() {
        if(isSelected) return;
        SideBar.curSelect.setSelected(false);
        SideBar.curSelect.setNoHover();
        SideBar.curSelect = this;
        isSelected = true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setNoHover();
        container.setPrefWidth(smallsz);
        name.setVisible(false);
        name.setText(ItemName);
        try {
            FileInputStream fis = new FileInputStream(unselectPath);
            unselectImg = new Image(fis,imgW,imgH,false,false);
        } catch (FileNotFoundException e) {
            System.out.println("file not found in SideBarItem");
            System.out.println(e.getMessage());
        }
        img.setImage(unselectImg);

        try {
            FileInputStream fis = new FileInputStream(selectPath);
            selectImg = new Image(fis,imgW,imgH,false,false);
        } catch (FileNotFoundException e) {
            System.out.println("file not found in SideBarItem");
            System.out.println(e.getMessage());
        }

        setNoHover();

        container.setOnMouseEntered(event -> {
            if(isSelected) return;
            setHover();
        });
        container.setOnMouseExited(event -> {
            if(isSelected) return;
            setNoHover();
        });

    }
}
