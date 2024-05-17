package com.example.hhd.Games.Scrabble;

import com.example.hhd.App;
import com.example.hhd.Algo.Dictionary;
import com.example.hhd.Algo.TrieDictionary;
import com.example.hhd.AppController;
import com.example.hhd.Games.GamesController;
import com.example.hhd.Games.Hangman.HangmanInfoController;
import com.example.hhd.SideBar.SideBar;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ScrabbleController extends AnchorPane{
//    @FXML
//    private ImageView box1, box2, box3, box4, box5, box6, box7;
//
//    private ArrayList<ImageView> boxes = new ArrayList<>();

    @FXML
    private GridPane ScrabbleContainer;
    @FXML
    private AnchorPane container;
    @FXML
    private HBox ScrabbleLetterContainer;
    @FXML
    private Label CurrentPoint;
    @FXML
    private Button btn1;
    @FXML
    private Button btn2;
    @FXML
    private Button btn3;
    @FXML
    private ImageView imgV1;
    @FXML
    private ImageView imgV2;
    @FXML
    private ImageView imgV3;
    private boolean dropped = true;
    private int sz = 30;
    private int sz2 = 75;
    ClipboardContent cc = new ClipboardContent();
    //private ImageView curDrag;
    private String curCharDrag;
    private int curPos;
    ArrayList<ArrayList<ScrabbleBoardWordController>> board = new ArrayList<>();
    ArrayList<ArrayList<Integer>> powerup = new ArrayList<>();
    private Dictionary data = AppController.data;
    private ArrayList<ScrabblePlayerLetterController> player = new ArrayList<>();
    //private HashMap<String,Integer> randomBag = new HashMap<>();
    private ArrayList<Integer> randomBag = new ArrayList<>();
    private ArrayList<Integer> point = new ArrayList<>();
    private Integer TileSize = 98;
    private Integer curPoint = 0;
    public ScrabbleController() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Games/Scrabble/Scrabble.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void initRandomBag() throws FileNotFoundException {
        randomBag.clear();
        File myFile = new File("HHD/src/main/resources/Scrabble/Image/Bag.txt");
        Scanner scanner = new Scanner(myFile);
        while (scanner.hasNext()){
            String string = scanner.nextLine();
            //String key = String.valueOf(string.charAt(0));
            String value = string.substring(2);
            Integer val = Integer.valueOf(value);
            randomBag.add(val);
        }
    }

    public void resetGame() throws FileNotFoundException {
        initRandomBag();
        TileSize = 98;
        curPoint = 0;
        String yourPoint = "Your Point: " + curPoint;
        CurrentPoint.setText(yourPoint);
        for(int i = 0; i < 15; i++) {
            for(int j = 0; j < 15; j++) {
                board.get(i).get(j).retreat();
                board.get(i).get(j).setDisable(false);
                board.get(i).get(j).setUnSealed();
            }
        }
        for (int i = 0; i < 7 ; i++) {
            //System.out.println(getRandomTile() + " ");
            player.get(i).setChar(getRandomTile());
            player.get(i).setDisable(false);
            player.get(i).setVisible(true);
        }
    }

    public void initialize() throws FileNotFoundException {
        File myObj = new File("HHD/src/main/resources/Scrabble/Image/Board.txt");
        Scanner myReader = new Scanner(myObj);
        for(int i = 0; i < 15; i++) {
            ArrayList<Integer> arr = new ArrayList<>();
            for(int j = 0 ; j < 15; j++) {
                arr.add(myReader.nextInt());
            }
            powerup.add(arr);
        }
        initRandomBag();
        File myFile1 = new File("HHD/src/main/resources/Scrabble/Image/Point.txt");
        Scanner scanner1 = new Scanner(myFile1);
        for(int i = 1 ; i <= 26 ; i++){
            point.add(scanner1.nextInt());
        }
        for(int i = 1; i <= 14 ; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPrefWidth(sz);
            column.setMaxWidth(sz);
            column.setMinWidth(sz);
            column.setHgrow(Priority.NEVER);
            ScrabbleContainer.getColumnConstraints().add(column);

            RowConstraints row = new RowConstraints();
            row.setMaxHeight(sz);
            row.setMinHeight(sz);
            row.setMinHeight(sz);
            row.setVgrow(Priority.NEVER);
            ScrabbleContainer.getRowConstraints().add(row);
        }

        for(int i = 0 ; i < 15 ; i++) {
            ArrayList<ScrabbleBoardWordController> arr = new ArrayList<>();
            for(int j = 0 ; j < 15 ; j++) {
                ScrabbleBoardWordController lb = new ScrabbleBoardWordController();
                lb.setId(i+"_"+j);
                lb.setXpos(i);
                lb.setYpos(j);
                lb.setPrefSize(30,30);
                int k = powerup.get(i).get(j);
                switch (k) {
                    case 1:
                        lb.setDL();
                        break;
                    case 2:
                        lb.setTL();
                        break;
                    case 3:
                        lb.setDW();
                        break;
                    case 4:
                        lb.setTW();
                        break;
                }
                lb.setOnDragEntered(event -> {
                    onDragEnter(event);
                });
                lb.setOnDragExited(event -> {
                    onDragExit(event);
                });
                lb.setOnDragOver(event ->  {
                    event.acceptTransferModes(TransferMode.ANY);
                    event.consume();
                });
                lb.setOnDragDropped(event -> {
                    //System.out.println("goes here");
                    //System.out.println(lb.isSealed());
                    if(!lb.isSealed() && !lb.isChoosed()){
                        dropped = true;
                        //System.out.println("drop");
                        try {
                            onDragDropped(event);
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        lb.setCharacter(curCharDrag);
                        curCharDrag = null;
                        //TODO: complete
                        event.consume();
                    }else{
                        dropped = false;
                    }
                });
                lb.setOnDragDetected(event -> {
                    onDrag(event);
                });
                lb.setOnDragDone(event -> {
                    if(dropped){
                        lb.retreat();
                    }
                });
                ScrabbleContainer.add(lb,j,i);
                arr.add(lb);
            }
            board.add(arr);
        }
        for(int i = 0 ; i < 7 ; i++){
            ScrabblePlayerLetterController splc = new ScrabblePlayerLetterController();
            splc.pos = i;
            String randomLetter = getRandomTile();
            splc.setChar(randomLetter);
            splc.setOnDragDetected(event -> {
                onDrag(event);
            });
            splc.setOnDragDone(event -> {
                if(dropped){
                    splc.setDisable(true);
                    splc.setVisible(false);
                }
            });
            //TODO: drag and stuff at splc
            ScrabbleLetterContainer.getChildren().add(splc);
            player.add(splc);
        }
        container.setOnDragDropped(event -> {
            if(cc.getString().equals("nope")) return;
            dropped = true;
            if (curPos >= 0) {
                player.get(curPos).setVisible(true);
                player.get(curPos).setDisable(false);
            } {
                curPos = -1;
                curCharDrag = "-";
            }
        });
        container.setOnDragOver(event ->  {
            event.acceptTransferModes(TransferMode.ANY);
            event.consume();
        });

        setUpBtn(btn1);
        setUpBtn(btn2);
        setUpBtn(btn3);

        GamesController.setHoverEffect(imgV1,imgV2,imgV3);

        AnchorPane anchorPane = new AnchorPane();
        try {
            ScrabbleInfoController info = new ScrabbleInfoController();
            anchorPane.getChildren().add(info);
            ImageView imageView = info.getImg();
            imageView.setOnMouseClicked(event -> {
                popup.hide();
            });
            popup.getContent().add(anchorPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setUpBtn(Button btn) {
        btn.setOnMouseEntered(event -> {
            btn.setStyle("-fx-background-color: #f7b63e; -fx-background-radius: 20");
        });
        btn.setOnMouseExited(event -> {
            btn.setStyle("-fx-background-color: orange; -fx-background-radius: 20");
        });
    }

    Random random = new Random();
    private String getRandomTile() {
        char c = '-';
        int num = random.nextInt(TileSize)+1;
        //System.out.print(num + " " + randomBag.size() + " ");
        for(int i = 0 ;i < randomBag.size(); i++) {
            if(randomBag.get(i)>0){
                num = num - randomBag.get(i);
                if(num<=0){
                    //System.out.print(i + " ");
                    c = (char) (i+65);
                    randomBag.set(i,randomBag.get(i)-1);
                    TileSize--;
                    break;
                }
            }
        }
        return String.valueOf(c);
    }

    @FXML
    public void onDrag(MouseEvent event) {
        dropped = false;
        Node node = (Node) event.getSource();
        if (node instanceof ScrabblePlayerLetterController) {
            curCharDrag = ((ScrabblePlayerLetterController) node).getCurLetter();
            curPos = ((ScrabblePlayerLetterController) node).pos;
        } else {
            if (node instanceof ScrabbleBoardWordController) {
                if(((ScrabbleBoardWordController) node).isSealed() || ((ScrabbleBoardWordController) node).getCharacter() == "?") return;
                curCharDrag = ((ScrabbleBoardWordController) node).getCharacter();
                curPos = ((ScrabbleBoardWordController) node).pos;
            } else{
                return;
            }
        }
        Dragboard db = node.startDragAndDrop(TransferMode.ANY);
        if(event.getSource() instanceof ScrabblePlayerLetterController) {
            cc.putString("nope");
        } else {
            cc.putString("Something");
        }
        db.setContent(cc);
    }

    @FXML
    public void onDragEnter(Event event) {
        //System.out.println("enter");
        Node node = (Node) event.getSource();
        if (node instanceof ScrabbleBoardWordController) {
            ((ScrabbleBoardWordController) node).setDragEnter();
        }
    }

    @FXML
    public void onDragExit(Event event) {
        //System.out.println("exit");
        Node node = (Node) event.getSource();
        if (node instanceof ScrabbleBoardWordController) {
            ((ScrabbleBoardWordController) node).setDragExit();
        }
    }

    @FXML
    public void onDragDropped(Event event) throws FileNotFoundException {
        //System.out.println("dropped");
        Node node = (Node) event.getSource();
        if(node instanceof ScrabbleBoardWordController) {
            ((ScrabbleBoardWordController) node).setImage(curCharDrag);
            ((ScrabbleBoardWordController) node).setCharacter(curCharDrag);
            ((ScrabbleBoardWordController) node).pos = curPos;
            ((ScrabbleBoardWordController) node).setChoosed(true);
        }
    }

    // TODO: test this
    public void LoadGames(Event event) {
        SideBar.loadGames();
    }

    private boolean checkValid() {
        for(int i = 0 ; i < 15; i++){
            for(int j = 0; j < 15; j++){
                if(board.get(i).get(j).getCharacter()!="?") {
                    String word = "";
                    int l = -1;
                    int r = -1;
                    for(int k = j ; k >= 0; k--){
                        if(board.get(i).get(k).getCharacter()!="?"){
                            l = k;
                        } else{
                            break;
                        }
                    }
                    for(int k = j ; k < 15; k++){
                        if(board.get(i).get(k).getCharacter()!="?"){
                            r = k;
                        } else{
                            break;
                        }
                    }
                    for(int k = l; k <= r; k++){
                        word += board.get(i).get(k).getCharacter();
                    }
                    //System.out.println(word);
                    word = word.toLowerCase();
                    if(!data.contains(word)){
                        return false;
                    }
                    word = "";
                    l = -1;
                    r = -1;
                    for(int k = i ; k >= 0; k--){
                        if(board.get(k).get(j).getCharacter()!="?"){
                            l = k;
                        } else{
                            break;
                        }
                    }
                    for(int k = i ; k < 15; k++){
                        if(board.get(k).get(j).getCharacter()!="?"){
                            r = k;
                        } else{
                            break;
                        }
                    }
                    for(int k = l; k <= r; k++) {
                        word += board.get(k).get(j).getCharacter();
                    }
                    //System.out.println(word);
                    word = word.toLowerCase();
                    if(!data.contains(word)){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean checkPlay() {
        ArrayList<ScrabbleBoardWordController> played = new ArrayList<>();
        for(int i = 0 ;i < 15; i++) {
            for(int j = 0; j < 15; j++) {
                if(board.get(i).get(j).isChoosed()) {
                    played.add(board.get(i).get(j));
                }
            }
        }
        //System.out.println(played);
        if(played.size()==0) {
            return false;
        }
        if(played.size()==1) {
            for(int i = 0 ; i < played.size(); i++){
                if(checkNeighbourSealed(played.get(i).getXpos(),played.get(i).getYpos())){
                    return true;
                }else{
                    return false;
                }
            }
        }
        if(played.size()>=2){
            int x = -1;
            int y = -1;
            for(int i = 0 ; i < played.size(); i++){
                if(checkNeighbourSealed(played.get(i).getXpos(),played.get(i).getYpos())){
                    x = played.get(i).getXpos();
                    y = played.get(i).getYpos();
                    break;
                }
            }
            //System.out.println(x + " " + y);
            if(x==-1||y==-1) return false;
            if(played.get(0).getXpos()==played.get(1).getXpos()) {
                x = played.get(0).getXpos();
                y = -1;
                //System.out.println(x);
            }else{
                if(played.get(0).getYpos()==played.get(1).getYpos()){
                    y = played.get(0).getYpos();
                    x = -1;
                    //System.out.println(y);
                } else{
                    //System.out.println("Not in line");
                    return false;
                }
            }

            if(x!=-1) {
                for(int i = 0 ;i < played.size(); i++){
                    if(played.get(i).getXpos()!=x){
                        //System.out.println("Not in Xline");
                        return false;
                    }
                }
            }else{
                for(int i = 0 ;i < played.size(); i++){
                    if(played.get(i).getYpos()!=y){
                        //System.out.println("Not in Yline");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean checkNeighbourSealed(int x, int y) {
        if(x>0){
            if(board.get(x-1).get(y).isSealed()) return true;
        }
        if(x<14){
            if(board.get(x+1).get(y).isSealed()) return true;
        }
        if(y>0){
            if(board.get(x).get(y-1).isSealed()) return true;
        }
        if(y<14){
            if(board.get(x).get(y+1).isSealed()) return true;
        }
        if(x==7&&y==7){
            return true;
        }
        return false;
    }

    @FXML
    public void onBtnSubmit(Event event) throws FileNotFoundException {
        if(!checkPlay()){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Wrong played!!!");
            a.setHeaderText("Invalid play");
            a.show();
            return;
        }

        if(!checkValid()){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Invalid");
            a.setHeaderText("Invalid Word");
            a.show();
            return;
        }

        ArrayList<ArrayList<Integer>> flg = new ArrayList<>();
        for(int i = 0; i < 15; i++) {
            ArrayList<Integer> tmp = new ArrayList<>();
            for(int j = 0; j < 15 ; j++){
                tmp.add(0);
                if(board.get(i).get(j).isChoosed()) {
                    String ss = board.get(i).get(j).getCharacter();
                    char cc = ss.charAt(0);
                    Integer num = cc-65;
                    board.get(i).get(j).setPoint(point.get(num));
                    if(powerup.get(i).get(j)==1){
                        board.get(i).get(j).setPoint(point.get(num)*2);
                    }
                    if(powerup.get(i).get(j)==2){
                        board.get(i).get(j).setPoint(point.get(num)*3);
                    }
                }
            }
            flg.add(tmp);
        }

        // 0: not yet count
        // 1: ngang
        // 2: doc
        // 3: done

        for(int i = 0; i < 15 ; i++){
            for(int j = 0; j < 15; j++){
                if(board.get(i).get(j).isChoosed()&&flg.get(i).get(j)!=3){
                    if(flg.get(i).get(j)==0){
                        // count ngang
                        int sm = 0;
                        int cnt = 0;
                        int mul = 1;
                        for(int k = j; k >= 0; k--){
                            if(board.get(i).get(k).isSealed()||board.get(i).get(k).isChoosed()) {
                                cnt++;
                                if(flg.get(i).get(k)==0){
                                    ArrayList<Integer> tmp = flg.get(i);
                                    tmp.set(k,1);
                                    flg.set(i,tmp);
                                }
                                if(flg.get(i).get(k)==2){
                                    ArrayList<Integer> tmp = flg.get(i);
                                    tmp.set(k,3);
                                    flg.set(i,tmp);
                                }
                                if(powerup.get(i).get(k)==3){
                                    mul *= 2;
                                }
                                if(powerup.get(i).get(k)==4){
                                    mul *= 3;
                                }
                                sm += board.get(i).get(k).getPoint();
                            }else{
                                break;
                            }
                        }
                        for(int k = j+1; k < 15; k++){
                            if(board.get(i).get(k).isSealed()||board.get(i).get(k).isChoosed()) {
                                cnt++;
                                if(flg.get(i).get(k)==0){
                                    ArrayList<Integer> tmp = flg.get(i);
                                    tmp.set(k,1);
                                    flg.set(i,tmp);
                                }
                                if(flg.get(i).get(k)==2){
                                    ArrayList<Integer> tmp = flg.get(i);
                                    tmp.set(k,3);
                                    flg.set(i,tmp);
                                }
                                if(powerup.get(i).get(k)==3){
                                    mul *= 2;
                                }
                                if(powerup.get(i).get(k)==4){
                                    mul *= 3;
                                }
                                sm += board.get(i).get(k).getPoint();
                            }else{
                                break;
                            }
                        }
                        if(cnt>1) curPoint += sm*mul;

                        cnt = 0;
                        sm = 0;
                        mul = 1;
                        // doc
                        for(int k = i; k >= 0; k--){
                            if(board.get(k).get(j).isSealed()||board.get(k).get(j).isChoosed()) {
                                cnt++;
                                if(flg.get(k).get(j)==0){
                                    ArrayList<Integer> tmp = flg.get(k);
                                    tmp.set(j,2);
                                    flg.set(k,tmp);
                                }
                                if(flg.get(k).get(j)==1){
                                    ArrayList<Integer> tmp = flg.get(k);
                                    tmp.set(j,3);
                                    flg.set(k,tmp);
                                }
                                if(powerup.get(k).get(j)==3){
                                    mul *= 2;
                                }
                                if(powerup.get(k).get(j)==4){
                                    mul *= 3;
                                }
                                sm += board.get(k).get(j).getPoint();
                            }else{
                                break;
                            }
                        }

                        for(int k = i+1; k < 15; k++){
                            if(board.get(k).get(j).isSealed()||board.get(k).get(j).isChoosed()) {
                                cnt++;
                                if(flg.get(k).get(j)==0){
                                    ArrayList<Integer> tmp = flg.get(k);
                                    tmp.set(j,2);
                                    flg.set(k,tmp);
                                }
                                if(flg.get(k).get(j)==1){
                                    ArrayList<Integer> tmp = flg.get(k);
                                    tmp.set(j,3);
                                    flg.set(k,tmp);
                                }
                                if(powerup.get(k).get(j)==3){
                                    mul *= 2;
                                }
                                if(powerup.get(k).get(j)==4){
                                    mul *= 3;
                                }
                                sm += board.get(k).get(j).getPoint();
                            }else{
                                break;
                            }
                        }
                        if(cnt>1) curPoint += sm*mul;

                    }else{
                        if(flg.get(i).get(j)==1){
                            int sm = 0;
                            int mul = 1;
                            int cnt = 0;

                            for(int k = i; k >= 0; k--){
                                if(board.get(k).get(j).isSealed()||board.get(k).get(j).isChoosed()) {
                                    cnt++;
                                    if(flg.get(k).get(j)==0){
                                        ArrayList<Integer> tmp = flg.get(k);
                                        tmp.set(j,2);
                                        flg.set(k,tmp);
                                    }
                                    if(flg.get(k).get(j)==1){
                                        ArrayList<Integer> tmp = flg.get(k);
                                        tmp.set(j,3);
                                        flg.set(k,tmp);
                                    }
                                    if(powerup.get(k).get(j)==3){
                                        mul *= 2;
                                    }
                                    if(powerup.get(k).get(j)==4){
                                        mul *= 3;
                                    }
                                    sm += board.get(k).get(j).getPoint();
                                }else{
                                    break;
                                }
                            }

                            for(int k = i+1; k < 15; k++){
                                if(board.get(k).get(j).isSealed()||board.get(k).get(j).isChoosed()) {
                                    cnt++;
                                    if(flg.get(k).get(j)==0){
                                        ArrayList<Integer> tmp = flg.get(k);
                                        tmp.set(j,2);
                                        flg.set(k,tmp);
                                    }
                                    if(flg.get(k).get(j)==1){
                                        ArrayList<Integer> tmp = flg.get(k);
                                        tmp.set(j,3);
                                        flg.set(k,tmp);
                                    }
                                    if(powerup.get(k).get(j)==3){
                                        mul *= 2;
                                    }
                                    if(powerup.get(k).get(j)==4){
                                        mul *= 3;
                                    }
                                    sm += board.get(k).get(j).getPoint();
                                }else{
                                    break;
                                }
                            }
                            if(cnt>1) curPoint += sm*mul;
                        }else{
                            if(flg.get(i).get(j)==2){
                                int cnt = 0;
                                int sm = 0;
                                int mul = 1;
                                for(int k = j; k >= 0; k--){
                                    if(board.get(i).get(k).isSealed()||board.get(i).get(k).isChoosed()) {
                                        cnt++;
                                        if(flg.get(i).get(k)==0){
                                            ArrayList<Integer> tmp = flg.get(i);
                                            tmp.set(k,1);
                                            flg.set(i,tmp);
                                        }
                                        if(flg.get(i).get(k)==2){
                                            ArrayList<Integer> tmp = flg.get(i);
                                            tmp.set(k,3);
                                            flg.set(i,tmp);
                                        }
                                        if(powerup.get(i).get(k)==3){
                                            mul *= 2;
                                        }
                                        if(powerup.get(i).get(k)==4){
                                            mul *= 3;
                                        }
                                        sm += board.get(i).get(k).getPoint();
                                    }else{
                                        break;
                                    }
                                }
                                for(int k = j+1; k < 15; k++){
                                    if(board.get(i).get(k).isSealed()||board.get(i).get(k).isChoosed()) {
                                        cnt++;
                                        if(flg.get(i).get(k)==0){
                                            ArrayList<Integer> tmp = flg.get(i);
                                            tmp.set(k,1);
                                            flg.set(i,tmp);
                                        }
                                        if(flg.get(i).get(k)==2){
                                            ArrayList<Integer> tmp = flg.get(i);
                                            tmp.set(k,3);
                                            flg.set(i,tmp);
                                        }
                                        if(powerup.get(i).get(k)==3){
                                            mul *= 2;
                                        }
                                        if(powerup.get(i).get(k)==4){
                                            mul *= 3;
                                        }
                                        sm += board.get(i).get(k).getPoint();
                                    }else{
                                        break;
                                    }
                                }
                                if(cnt>1) curPoint += sm*mul;
                            }
                        }
                    }
                }
            }
        }

        for(int i = 0 ; i < 15 ; i++){
            for(int j = 0; j < 15; j++){
                if(board.get(i).get(j).isChoosed()) {
                    board.get(i).get(j).setChoosed(false);
                    board.get(i).get(j).setSealed();
                }
            }
        }
        for(int i = 0; i < player.size(); i++){
            if(player.get(i).isDisable()){
                player.get(i).setChar(getRandomTile());
                player.get(i).setDisable(false);
                player.get(i).setVisible(true);
            }
        }


        String yourpoint = "Your Point: " + curPoint;
        CurrentPoint.setText(yourpoint);

    }

    @FXML
    public void RetreatLetter(Event event) {
        for(int i = 0 ; i < 15 ; i++) {
            for(int j = 0; j < 15 ;j++) {
                if(board.get(i).get(j).isChoosed()&& !board.get(i).get(j).isSealed()) {
                    board.get(i).get(j).retreat();
                }
            }
        }
        for(int i = 0; i < 7 ; i++){
            player.get(i).setDisable(false);
            player.get(i).setVisible(true);
        }
    }

    public void ResetBag() throws FileNotFoundException {
        for(int i = 0;i < 7; i++) {
            if(player.get(i).isDisable()){
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText("You must return all letter before swap");
                a.show();
                return;
            }
        }
        for (int i = 0; i < 7 ; i++) {
            String k = player.get(i).getCurLetter();
            k = k.toLowerCase();
            int c = k.charAt(0) - 'a';
            TileSize++;
            randomBag.set(c,randomBag.get(c)+1);
            player.get(i).setChar(getRandomTile());
        }
    }

    private Popup popup = new Popup();
    public void showInfo(Event event) {
        popup.show(ScrabbleContainer.getScene().getWindow()
                ,ScrabbleContainer.localToScreen(0,0).getX()
                ,ScrabbleContainer.localToScreen(0,0).getY());
    }
}

