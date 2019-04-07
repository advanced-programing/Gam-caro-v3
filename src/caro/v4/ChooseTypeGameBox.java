/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caro.v4;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.text.*;
import javafx.scene.paint.Color;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
/**
 *
 * @author USER
 */
public class ChooseTypeGameBox {
    static GameType gameType;
    static Image menuImage = new Image("/image/pic10.jpg");
    
    public static GameType display(){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Choose game type");
        
        Label message = new Label(" Choose Game Type You Want To Play ");
        Button typeHuman = new Button("Play with another player");
        Button typeCom = new Button("Play with computer");
        
        message.setFont( Font.font("Verdana", FontWeight.NORMAL, 25));
        message.setTextFill( Color.BLUE);
        typeHuman.setStyle("-fx-font-size:14px;");
        typeHuman.setPrefWidth(200);
        typeCom.setStyle("-fx-font-size:14px;");
        typeCom.setPrefWidth(200);
        
        typeHuman.setOnAction(e->{
            gameType = GameType.HUMAN;
            window.close();
        });


        typeCom.setOnAction(e -> {
            Stage window2 = new Stage();
            window2.initModality(Modality.APPLICATION_MODAL);
            window2.setTitle("Choose level ");
            Button button = new Button("Apply");
            
            ListView<String> listView = new ListView<>();
            listView.getItems().addAll("Level 1", "Level 2", "Level 3");
            
            button.setOnAction(event -> {
                ObservableList<String> type;
                type = listView.getSelectionModel().getSelectedItems();
                if(type.get(0).equals("Level 1"))
                    gameType = GameType.COMLEVEL1;
                else if(type.get(0).equals("Level 2"))
                    gameType = GameType.COMLEVEL2;
                else if(type.get(0).equals("Level 3"))
                    gameType = GameType.COMLEVEL3;
                System.out.println(gameType + "");  
            });
            
            VBox layout = new VBox(10);
            layout.setPadding(new Insets(20, 20, 20, 20));
            layout.getChildren().addAll(listView, button);

            Scene scene2 = new Scene(layout, 300, 200);
            window2.setScene(scene2);
            window2.showAndWait();
        });

        VBox buttonBar = new VBox();
        buttonBar.setSpacing(10);
        buttonBar.setPadding(new Insets(20, 20, 20, 120)); 
        buttonBar.getChildren().addAll(typeHuman, typeCom);
        
        BorderPane root = new BorderPane();
        root.setCenter(buttonBar);
        root.setTop(message);
       
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.showAndWait();
        return gameType;
    }
}
