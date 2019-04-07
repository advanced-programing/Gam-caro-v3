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

public class UserInforBox {
    static String name1;
    static String name2;
    
    public static void display(){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        
        Label label1 = new Label("  What is the first player's name?  ");
        Label label2 = new Label(" What is the second player's name? ");
        Button button = new Button("Apply");
        TextField nameInput1 = new TextField();
        TextField nameInput2 = new TextField();
        
        label1.setFont( Font.font("Arial", FontWeight.LIGHT, 20));
        label2.setFont( Font.font("Arial", FontWeight.LIGHT, 20));
        
        button.setStyle("-fx-font-size:14px;");
        button.setOnAction( e -> {
            if(isValid(nameInput1)&&isValid(nameInput2)){
                name1 = nameInput1.getText();
                name2 = nameInput2.getText();
                window.close();
            }
        } );
        
        VBox root = new VBox();
        root.setSpacing(10);
        root.setPadding(new Insets(20, 20, 20, 20)); 
        root.getChildren().addAll(label1, nameInput1, label2, nameInput2, button);
        
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.setTitle("Enter User Infor Box");
        window.showAndWait(); 
    }
    
    private static boolean isValid(TextField name){
        try{
            if(name.getText().isEmpty()==false)
                System.out.println("Player's name: " + name.getText());
            return true;
        }catch(NumberFormatException e){
            System.out.println("Player's name can not empty");
            return false;
        }
    }
}
    
