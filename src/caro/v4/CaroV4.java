/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caro.v4;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 *
 * @author USER
 */
public class CaroV4 extends Application {
    private int ROWS = 6;
    private int COLS = 6;
    private boolean playable = true;
    private GameState currentState = GameState.PLAYING;
    private Cell[][] board = new Cell[ROWS][COLS];
    private Seed currentPlayer = Seed.X;
    private List<Combo> combos = new ArrayList<>();
    
    @Override
    public void start(Stage primaryStage) {
        Label status = new Label("Playing...");
        Button newGame = new Button("New Game");
        Button quit = new Button("Quit");
        Button size1 = new Button("3x3");
        Button size2 = new Button("6x6");
                
        status.setStyle("-fx-font-size:14px;");
        newGame.setStyle("-fx-font-size:14px;");
        quit.setStyle("-fx-font-size:14px;");
        
        Pane boardPane = new Pane();
        boardPane.setPrefSize(600, 600);
        //initGame(boardPane);
        size1.setOnAction(e->{
            ROWS = 3;
            COLS = 3;
            initBoard(boardPane);
            initGame();
        });
        size2.setOnAction(e->{
            ROWS = 6;
            COLS = 6;
            initBoard(boardPane);
            initGame();
        });
        boardPane.setOnMouseClicked(e->{
            int r = (int)e.getSceneX()/(600/ROWS);
            int c = (int)e.getSceneY()/(600/COLS);
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
            }
        });
        newGame.setOnAction(e ->{
            status.setText("Playing...");
            initGame();
        });
        quit.setOnAction(e -> Platform.exit());
        
        VBox vbButtons = new VBox();
        vbButtons.setSpacing(10);
        vbButtons.setPadding(new Insets(0, 20, 10, 20)); 
        vbButtons.getChildren().addAll(newGame, quit, size1, size2);
        
        BorderPane root = new BorderPane();
        root.setCenter(boardPane);
        root.setRight(vbButtons);
        root.setBottom(status);
        
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void initGame(){
         for(int r=0; r<=ROWS-3; r++){
            for(int c=2; c<COLS; c++){
                combos.add(new Combo(board[r][c], board[r+1][c-1], board[r+2][c-2]));
            }
        }
        for(int r=0; r<ROWS; r++){
            for(int c=0; c<COLS; c++){
                board[r][c].setColorText(Color.BLACK);
                board[r][c].drawEmpty();
                board[r][c].setIsChecked(false);
            }
        }
        
        //playable = true;
        currentState = GameState.PLAYING;
        currentPlayer = Seed.X;
    }
    public void initBoard(Pane boardPane){
        for(int r=0; r<ROWS; r++){
            for(int c=0; c<COLS; c++){
                Cell cell = new Cell(600/ROWS);
                cell.setTranslateX(r*(600/ROWS));
                cell.setTranslateY(c*(600/COLS));
                boardPane.getChildren().add(cell);
                
                board[r][c] = cell;
            }
        }
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
    }
    public boolean checkState() {
        for (Combo combo : combos) {
            if (combo.isComplete()) {
                combo.cells[0].setColorText(Color.RED);
                combo.cells[1].setColorText(Color.RED);
                combo.cells[2].setColorText(Color.RED);
                return true;
            }
        }
        return false;
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
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
