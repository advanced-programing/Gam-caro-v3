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
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.effect.BlendMode;
import javafx.util.Duration;

/**
 *
 * @author USER
 */
public class CaroV4 extends Application {
    private boolean playable = true;
    private Cell[][] board = new Cell[3][3];
    private Seed currentPlayer = Seed.X;
    private List<Combo> combos = new ArrayList<>();
    @Override
    public void start(Stage primaryStage) {
        Button newGame = new Button("New Game");
        Button quit = new Button("Quit");
        Line line = new Line();
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
            if(playable){
                if(currentPlayer == Seed.X && board[r][c].getIsChecked()==false)
                    board[r][c].drawX();
                else if(currentPlayer == Seed.O && board[r][c].getIsChecked()==false)
                    board[r][c].drawO();
                currentPlayer = (currentPlayer==Seed.X)?Seed.O:Seed.X;
                board[r][c].setIsChecked(true);
                checkState(line);
                boardPane.getChildren().add(line);
            }
        });
        newGame.setOnAction(e ->{
            //line.disableProperty();
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
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void initGame(){
        for(int r=0; r<3; r++)
            for(int c=0; c<3; c++)
                board[r][c].drawEmpty();
        playable = true;
        currentPlayer = Seed.X;
    }
    public void checkState(Line line) {
        for (Combo combo : combos) {
            if (combo.isComplete()) {
                playable = false;
                playWinAnimation(line, combo);
                break;
            }
        }
    }
    
    public void playWinAnimation(Line line, Combo combo){
        //Line line = new Line();
        line.setStartX(combo.cells[0].getX());
        line.setStartY(combo.cells[0].getY());
        line.setEndX(combo.cells[0].getX());
        line.setEndY(combo.cells[0].getY());

        //root.getChildren().add(line);

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1),
                new KeyValue(line.endXProperty(), combo.cells[2].getX()),
                new KeyValue(line.endYProperty(), combo.cells[2].getY())));
        timeline.play();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
