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
    private boolean playable = true;
    private GameState currentState = GameState.PLAYING;
    private Cell[][] board = new Cell[3][3];
    private Seed currentPlayer = Seed.X;
    private List<Combo> combos = new ArrayList<>();
    
    @Override
    public void start(Stage primaryStage) {
        Label status = new Label();
        Button newGame = new Button("New Game");
        Button quit = new Button("Quit");
        status.setStyle("-fx-font-size:14px;");
        newGame.setStyle("-fx-font-size:14px;");
        quit.setStyle("-fx-font-size:14px;");
        
        Pane boardPane = new Pane();
        boardPane.setPrefSize(600, 600);
        for(int r=0; r<3; r++){
            for(int c=0; c<3; c++){
                Cell cell = new Cell();
                cell.setTranslateX(r*200);
                cell.setTranslateY(c*200);
                boardPane.getChildren().add(cell);
                
                board[r][c] = cell;
            }
        }
        for(int r=0; r<3; r++)
            combos.add(new Combo(board[r][0], board[r][1], board[r][2]));
        for(int c=0; c<3; c++)
            combos.add(new Combo(board[0][c], board[1][c], board[2][c]));
        combos.add(new Combo(board[0][0], board[1][1], board[2][2]));
        combos.add(new Combo(board[0][2], board[1][1], board[2][0]));
        boardPane.setOnMouseClicked(e->{
            int r = (int)e.getSceneX()/200;
            int c = (int)e.getSceneY()/200;
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
        vbButtons.getChildren().addAll(newGame, quit);
        
        BorderPane root = new BorderPane();
        root.setCenter(boardPane);
        root.setRight(vbButtons);
        root.setBottom(status);
        
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void initGame(){
        for(int r=0; r<3; r++){
            for(int c=0; c<3; c++){
                board[r][c].drawEmpty();
                board[r][c].setIsChecked(false);
            }
        }
        
        //playable = true;
        currentState = GameState.PLAYING;
        currentPlayer = Seed.X;
    }
    public boolean checkState() {
        for (Combo combo : combos) {
            if (combo.isComplete()) {
                return true;
            }
        }
        return false;
    }
    
    public boolean checkDraw(){
        for(int r=0; r<3; r++){
            for(int c=0; c<3; c++){
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
