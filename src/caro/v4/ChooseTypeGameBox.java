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
/**
 *
 * @author USER
 */
public class ChooseTypeGameBox {
    static int size;
    
    public static int display(){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        
        Label message = new Label(" Choose type of Game ");
        Button type1 = new Button("Play with another player");
        Button type2 = new Button("Play with computer");
        
        message.setStyle("-fx-font-size: 20pt; -fx-font-family: \"Segoe UI Light\";  -fx-text-fill: Blue; -fx-opacity: 1;");
        type1.setStyle("-fx-font-size:14px; -");
        type1.setPrefWidth(200);
        type2.setStyle("-fx-font-size:14px;");
        type2.setPrefWidth(200);
        
        type1.setOnAction(e->{
            size = 3;
            window.close();
        });
        
        type2.setOnAction(e->{
            size = 6;
            window.close();
        });
        
        VBox buttonBar = new VBox();
        buttonBar.setSpacing(10);
        buttonBar.setPadding(new Insets(20, 20, 20, 30)); 
        buttonBar.getChildren().addAll(type1, type2);
        
        BorderPane root = new BorderPane();
        root.setCenter(buttonBar);
        root.setTop(message);
        
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.showAndWait();
        return size;
    }
}
