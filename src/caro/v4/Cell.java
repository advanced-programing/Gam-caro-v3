/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caro.v4;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
/**
 *
 * @author USER
 */
 public class Cell extends StackPane{
        private Text text;
        private Rectangle border;
        private boolean isChecked;
        public Cell(){
            border = new Rectangle(200, 200);
            text = new Text();
            border.setFill(null);
            border.setStroke(Color.BLACK);
            text.setFont(Font.font(72));
            isChecked = false;
            
            setAlignment(Pos.CENTER);
            getChildren().addAll(border, text);
        }
        public String getText(){
            return text.getText();
        }
        public double getX(){
            return this.getTranslateX()+100;
        }
        public double getY(){
            return this.getTranslateY()+100;
        }
        public void drawX(){
            text.setText("X");
        }
        public void drawO(){
            text.setText("O");
        }
        public void drawEmpty(){
            text.setText(null);
        }
        public void setIsChecked(boolean val){
            isChecked = val;
        }
        public boolean getIsChecked(){
            return isChecked;
        }
    }

