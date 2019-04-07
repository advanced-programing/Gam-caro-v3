/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caro.v4;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.text.*;
import javafx.scene.paint.Color;


/**
 *
 * @author USER
 */
public class ChooseSizeBox {
    static int size;
    
    public static int display(){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        
        Label message = new Label("  Choose the GameBoard size  ");
        Button buttonSize1 = new Button("3x3");
        Button buttonSize2 = new Button("6x6");
        Button buttonSize3 = new Button("10x10");
        
        message.setFont( Font.font("Verdana", FontWeight.NORMAL, 25));
        message.setTextFill( Color.BLUE);
        
        buttonSize1.setStyle("-fx-font-size:14px;");
        buttonSize1.setPrefWidth(100);
        buttonSize2.setStyle("-fx-font-size:14px;");
        buttonSize2.setPrefWidth(100);
        buttonSize3.setStyle("-fx-font-size:14px;");
        buttonSize3.setPrefWidth(100);
        
        buttonSize1.setOnAction(e->{
            size = 3;
            window.close();
        });
        
        buttonSize2.setOnAction(e->{
            size = 6;
            window.close();
        });
        
        buttonSize3.setOnAction(e->{
            size = 10;
            window.close();
        });
        
        VBox buttonBar = new VBox();
        buttonBar.setSpacing(10);
        buttonBar.setPadding(new Insets(20, 20, 20, 150)); 
        buttonBar.getChildren().addAll(buttonSize1, buttonSize2, buttonSize3);
        
        BorderPane root = new BorderPane();
        root.setCenter(buttonBar);
        root.setTop(message);
        
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.setTitle("Choose Size Box");
        window.showAndWait();
        return size;
    }
}
