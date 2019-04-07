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

public class ConfirmBox {

    //Create variable
    static boolean answer;

    public static boolean display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(250);
        Label label = new Label(" Are you sure want to quit? ");
        
        //Create two buttons
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        label.setFont( Font.font("Arial", FontWeight.LIGHT, 15));
        yesButton.setStyle("-fx-font-size:14px;");
        yesButton.setPrefWidth(50);
        noButton.setStyle("-fx-font-size:14px;");
        noButton.setPrefWidth(50);
        //Clicking will set answer and close window
        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });
        noButton.setOnAction(e -> {
            answer = false;
            window.close();
        });

        VBox layout = new VBox(10);

        //Add buttons
        layout.getChildren().addAll(label, yesButton, noButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20, 20, 20, 20)); 
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.setTitle("Confirm Box");
        window.showAndWait();

        //Make sure to return answer
        return answer;
    }

}