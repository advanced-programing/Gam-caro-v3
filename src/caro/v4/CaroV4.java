/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caro.v4;

import java.util.ArrayList;
import java.util.List;
import javafx.util.Duration;

import javafx.application.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.animation.PauseTransition;

/**
 *
 * @author USER
 */
public class CaroV4 extends Application {
    private int ROWS = 10;
    private int COLS = 10;
    private GameState currentState = GameState.PLAYING;
    private Cell[][] board = new Cell[ROWS][COLS];
    private Seed currentPlayer = Seed.X;
    private Seed playerSeed = Seed.X;
    private Seed comSeed = Seed.O;
    private List<Combo> combos = new ArrayList<>();
    private int rowComMove=0;
    private int colComMove=0;
    private GameType gameType = GameType.HUMAN;
    //Stage window = new Stage();
     
    @Override
    public void start(Stage primaryStage) {
        Label label = new Label("  WELLCOME TO CARO GAME  ");
        Button start = new Button("Start");
        Button quit = new Button("Quit");
        Button chooseSize = new Button("Game size");
        Button chooseType = new Button("Game type");
                
        label.setStyle("-fx-font-size: 32pt; -fx-font-family: \"Segoe UI Light\";  -fx-text-fill: Green; -fx-opacity: 1;");
        start.setStyle("-fx-font-size:14px;");
        start.setPrefWidth(100);
        quit.setStyle("-fx-font-size:14px;");
        quit.setPrefWidth(100);
        chooseSize.setStyle("-fx-font-size:14px;");
        chooseSize.setPrefWidth(100);
        chooseType.setStyle("-fx-font-size:14px;");
        chooseType.setPrefWidth(100);
        
        chooseSize.setOnAction(e -> {
            int size = ChooseSizeBox.display();
            ROWS = size;
            COLS = size;
        });
        chooseType.setOnAction(e -> {
            gameType = ChooseTypeGameBox.display();
        });
        quit.setOnAction(e -> Platform.exit());
        
        start.setOnAction(e -> {
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Playing Caro game");
       
            Label status = new Label("Playing...");
            Button newGame = new Button("New Game");
            Button quitBoard = new Button("Quit");
                
            status.setStyle("-fx-font-size:20px; -fx-text-fill: Green;");
            newGame.setStyle("-fx-font-size:14px;");
            quitBoard.setStyle("-fx-font-size:14px;");
        
            Pane boardPane = new Pane();
            boardPane.setPrefSize(600, 600);
        
            initBoard(boardPane);
            initGame();
            initCombo();
            
            newGame.setOnAction(event ->{
                status.setText("Playing...");
                initGame();
            });
            System.out.println("Rows: "+ROWS+" Cols: "+COLS+"\n");
            
            boardPane.setOnMouseClicked(event->{
                int r = (int)event.getSceneX()/(600/ROWS);
                int c = (int)event.getSceneY()/(600/COLS);
                
                if(gameType == GameType.HUMAN){    
                    if(currentState == GameState.PLAYING){
                        if(currentPlayer == Seed.X && board[r][c].getIsChecked()==false)
                            board[r][c].drawX();
                        else if(currentPlayer == Seed.O && board[r][c].getIsChecked()==false)
                            board[r][c].drawO();
                        board[r][c].setIsChecked(true);
                        if(checkState()){
                            if(currentPlayer==Seed.X){
                                currentState = GameState.X_WIN;
                                status.setText("X won, game over!");
                            }
                            else{
                                currentState = GameState.O_WIN;
                                status.setText("O won, game over!");
                            }   
                        }
                        else if(checkDraw()){
                            currentState = GameState.DRAW;
                            status.setText("It's a draw, game over!");
                        }
                        else{
                            status.setText("Playing...");
                        }
                        currentPlayer = (currentPlayer==Seed.X)?Seed.O:Seed.X;
                        System.out.println(currentState + "");
                    }
                }
                else if(gameType == GameType.COMLEVEL1){
                    if(currentState == GameState.PLAYING){
                        status.setText("Computer move...");
                        if(board[r][c].getIsChecked() == false){
                            board[r][c].drawX();
                            board[r][c].setIsChecked(true);
                        
                        if(checkState()){
                                currentState = GameState.X_WIN;
                                status.setText("You won, game over!");   
                        }
                        else if(checkDraw()){
                            currentState = GameState.DRAW;
                            status.setText("It's a draw, game over!");
                        }
                        else{
                            PauseTransition delay = new PauseTransition(Duration.seconds(2));
                            delay.setOnFinished(eventDraw ->{
                                status.setText("You move...");
                                tableLookupComMove(r, c);
                                board[rowComMove][colComMove].drawO();
                                board[rowComMove][colComMove].setIsChecked(true);
                                
                                if(checkState()){
                                    currentState = GameState.O_WIN;
                                    status.setText("Computer won, game over!");   
                                }
                                else if(checkDraw()){
                                    currentState = GameState.DRAW;
                                    status.setText("It's a draw, game over!");
                                }
                            });
                            delay.play();
                        }
                    }
                }
                }
                
            });
            quitBoard.setOnAction(event -> closeWindow(window));
            window.setOnCloseRequest(event -> closeWindow(window));
            
            VBox vbButtons = new VBox();
            vbButtons.setSpacing(10);
            vbButtons.setPadding(new Insets(10, 20, 10, 20)); 
            vbButtons.getChildren().addAll(newGame, quitBoard);
        
            BorderPane rootBoard = new BorderPane();
            rootBoard.setCenter(boardPane);
            rootBoard.setRight(vbButtons);
            rootBoard.setBottom(status);
            status.setPadding(new Insets(10, 10, 20, 0));
        
            Scene sceneBoard = new Scene(rootBoard);
            window.setScene(sceneBoard);
            window.showAndWait();
        });
        
        VBox buttonBar = new VBox();
        buttonBar.setSpacing(10);
        buttonBar.setPadding(new Insets(20, 20, 20, 220)); 
        buttonBar.getChildren().addAll(chooseSize, chooseType, start, quit);

        BorderPane root = new BorderPane();
        root.setCenter(buttonBar);
        root.setTop(label);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
     public void initGame(){
        System.out.println("Init game");
        for(int r=0; r<ROWS; r++){
            for(int c=0; c<COLS; c++){
                board[r][c].setColorText(Color.BLACK);
                board[r][c].drawEmpty();
                board[r][c].setIsChecked(false);
            }
        }
        printCombo();
        //playable = true;
        currentState = GameState.PLAYING;
        currentPlayer = Seed.X;
    }
    public void initBoard(Pane boardPane){
        System.out.println("Init board");
        for(int r=0; r<ROWS; r++){
            for(int c=0; c<COLS; c++){
                Cell cell = new Cell(600/ROWS);
                cell.setTranslateX(r*(600/ROWS));
                cell.setTranslateY(c*(600/COLS));
                boardPane.getChildren().add(cell);
                
                board[r][c] = cell;
            }
        }
    }
    public void initCombo(){
        System.out.println("Init combo");
        for(int r=0; r<ROWS; r++){
            for(int c=0; c<=COLS-3; c++){
                combos.add(new Combo(board[r][c], board[r][c+1], board[r][c+2]));
            }
        }
        for(int c=0; c<COLS; c++){
            for(int r=0; r<=ROWS-3; r++){
                combos.add(new Combo(board[r][c], board[r+1][c], board[r+2][c]));
            }
        }
        for(int r=0; r<=ROWS-3; r++){
            for(int c=0; c<=COLS-3; c++){
                combos.add(new Combo(board[r][c], board[r+1][c+1], board[r+2][c+2]));
            }
        }
        for(int r=0; r<=ROWS-3; r++){
            for(int c=2; c<COLS; c++){
                combos.add(new Combo(board[r][c], board[r+1][c-1], board[r+2][c-2]));
            }
        }
        printCombo();
    }
    public void printCombo(){
        for(Combo combo : combos)
            System.out.println("Combo: "+combo.cells[0].getText()+" , "+combo.cells[0].getText()+" , "+combo.cells[0].getText());
    }
    public boolean checkState() {
        for (Combo combo : combos) {
            if (combo.isComplete()) {
                combo.cells[0].setColorText(Color.RED);
                combo.cells[1].setColorText(Color.RED);
                combo.cells[2].setColorText(Color.RED);
                System.out.println("Combo: "+combo.cells[0].getText()+" , "+combo.cells[0].getText()+" , "+combo.cells[0].getText());
                return true;
            }
        }
        return false;
    }
   
    public void closeWindow(Stage window){
        Boolean answer = ConfirmBox.display("Confirm Box", "Are you sure want to exit");
        if(answer){
            initGame();
            window.close();
        }
    }
    public boolean checkDraw(){
        for(int r=0; r<ROWS; r++){
            for(int c=0; c<COLS; c++){
                if(board[r][c].getIsChecked()==false)
                    return false;
            }
        }
        return true;
    }
    public void tableLookupComMove(int r, int c){
        //4 o ow tam
        if(c<COLS-1 && r<ROWS-1 && board[r+1][c+1].getIsChecked()==false){
            rowComMove = r + 1;
            colComMove = c + 1;
        }
        else if(c>=1 && r<ROWS-1 && board[r+1][c-1].getIsChecked()==false){
            rowComMove = r + 1;
            colComMove = c - 1;
        }
        else if(c<COLS-1 && r>=1 && board[r-1][c+1].getIsChecked()==false){
            rowComMove = r - 1;
            colComMove = c + 1;
        }
        else if(c>=1 && r>=1 && board[r-1][c-1].getIsChecked()==false){
            rowComMove = r - 1;
            colComMove = c - 1;
        }
        //7 o ow goc II
        else if(r>=2 && c>=2 && board[r-2][c-2].getIsChecked()==false){
            rowComMove = r-2;
            colComMove = c-2;
        }
        else if(r>=2 && board[r-2][c].getIsChecked()==false){
            rowComMove = r-2;
            colComMove = c;
        }
        else if(c>=2 && board[r][c-2].getIsChecked()==false){
            rowComMove = r;
            colComMove = c-2;
        }
        else if(r>=2 && c>=1 && board[r-2][c-1].getIsChecked()==false){
            rowComMove = r-2;
            colComMove = c-1;
        }
        else if(c>=1 && board[r][c-1].getIsChecked()==false){
            rowComMove = r;
            colComMove = c-1;
        }
        else if(r>=1 && board[r-1][c].getIsChecked()==false){
            rowComMove = r-1;
            colComMove = c;
        }
        else if(r>=1 && c>=2 && board[r-1][c-2].getIsChecked()==false){
            rowComMove = r-1;
            colComMove = c-2;
        }
        //4 o goc IV
        else if(c<COLS-2 && board[r][c+2].getIsChecked()==false){
            rowComMove = r;
            colComMove = c+2;
        }
        else if(r<ROWS-2 && board[r+2][c].getIsChecked()==false){
            rowComMove = r+2;
            colComMove = c;
        }
        else if(r<ROWS-2 && c<COLS-2 && board[r+2][c+2].getIsChecked()==false){
            rowComMove = r+2;
            colComMove = c+2;
        }
        else if(c<COLS-1 && board[r][c+1].getIsChecked()==false){
            rowComMove = r;
            colComMove = c+1;
        }
        else if(r<ROWS-2 && c<COLS-1 && board[r+2][c+1].getIsChecked()==false){
            rowComMove = r+2;
            colComMove = c+1;
        }
        else if(r<ROWS-1 && board[r+1][c].getIsChecked()==false){
            rowComMove = r+1;
            colComMove = c;
        }
        else if(c<COLS-2 && r<ROWS-1 && board[r+1][c+2].getIsChecked()==false){
            rowComMove = r+1;
            colComMove = c+2;
        }
    }
    
}
