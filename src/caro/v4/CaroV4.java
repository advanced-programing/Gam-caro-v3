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
import javafx.stage.Stage;

/**
 *
 * @author USER
 */
public class CaroV4 extends Application {
    private int ROWS = 10;
    private int COLS = 10;
    private boolean playable = true;
    private GameState currentState = GameState.PLAYING;
    private Cell[][] board = new Cell[ROWS][COLS];
    private Seed currentPlayer = Seed.X;
    private List<Combo> combos = new ArrayList<>();
    
    @Override
    public void start(Stage primaryStage) {
        Label label = new Label("  WELLCOME TO CARO GAME  ");
        Button start = new Button("Start");
        Button quit = new Button("Quit");
        Button chooseSize = new Button("Game size");
        Button chooseType = new Button("Game type");
                
        label.setStyle("-fx-font-size: 32pt; -fx-font-family: \"Segoe UI Light\";  -fx-text-fill: Green; -fx-opacity: 1;");
        start.setStyle("-fx-font-size:14px; -");
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
            ChooseTypeGameBox.display();
        });
        quit.setOnAction(e -> Platform.exit());
        
        start.setOnAction(e -> {
            GameBoard.setCols(COLS);
            GameBoard.setRows(ROWS);
            GameBoard.display();
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
    
}
