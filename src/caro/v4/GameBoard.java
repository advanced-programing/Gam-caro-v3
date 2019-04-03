/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caro.v4;

import java.util.ArrayList;
import java.util.List;

import javafx.application.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.*;
/**
 *
 * @author USER
 */
public class GameBoard {
    private static int ROWS = 10;
    private static int COLS = 10;
    private static boolean playable = true;
    private static GameState currentState = GameState.PLAYING;
    private static Cell[][] board = new Cell[ROWS][COLS];
    private static Seed currentPlayer = Seed.X;
    private static List<Combo> combos = new ArrayList<>();
    private static Stage window = new Stage();
    
    public static void display() {
       // Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Playing Caro game");
       
        Label status = new Label("Playing...");
        Button newGame = new Button("New Game");
        Button quit = new Button("Quit");
                
        status.setStyle("-fx-font-size:20px; -fx-text-fill: Green;");
        newGame.setStyle("-fx-font-size:14px;");
        quit.setStyle("-fx-font-size:14px;");
        
        Pane boardPane = new Pane();
        boardPane.setPrefSize(600, 600);
        
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
        for(int r=0; r<=ROWS-3; r++){
            for(int c=2; c<COLS; c++){
                combos.add(new Combo(board[r][c], board[r+1][c-1], board[r+2][c-2]));
            }
        }
        
        newGame.setOnAction(e ->{
            status.setText("Playing...");
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
        quit.setOnAction(e -> closeWindow());
        window.setOnCloseRequest(e -> closeWindow());
        
        VBox vbButtons = new VBox();
        vbButtons.setSpacing(10);
        vbButtons.setPadding(new Insets(10, 20, 10, 20)); 
        vbButtons.getChildren().addAll(newGame, quit );
        
        BorderPane root = new BorderPane();
        root.setCenter(boardPane);
        root.setRight(vbButtons);
        root.setBottom(status);
        status.setPadding(new Insets(10, 10, 20, 0));
        
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.showAndWait();
    }
    public static void initGame(){
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
    public static void initBoard(Pane boardPane){
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
        for(int r=0; r<=ROWS-3; r++){
            for(int c=2; c<COLS; c++){
                combos.add(new Combo(board[r][c], board[r+1][c-1], board[r+2][c-2]));
            }
        }
    }
    public static boolean checkState() {
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
    public static void setCols(int cols){
        COLS = cols;
    }
    public static void setRows(int rows){
        ROWS = rows;
    }
    public static void closeWindow(){
        Boolean answer = ConfirmBox.display("Confirm Box", "Are you sure want to exit");
        if(answer)
            window.close();
    }
    public static boolean checkDraw(){
        for(int r=0; r<ROWS; r++){
            for(int c=0; c<COLS; c++){
                if(board[r][c].getIsChecked()==false)
                    return false;
            }
        }
        return true;
    }
   
}
