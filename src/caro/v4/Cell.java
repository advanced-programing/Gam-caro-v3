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
        private Seed content;
        private Text text;
        private Rectangle border;
        private boolean isChecked;
        public Cell(double f){
            border = new Rectangle(f, f);
            text = new Text();
            border.setFill(null);
            border.setStroke(Color.BLACK);
            text.setFont(Font.font(40));
            text.setFill(Color.BLACK);
            isChecked = false;
            
            setAlignment(Pos.CENTER);
            getChildren().addAll(border, text);
        }
       
        public String getText(){
            return text.getText();
        }
        public void setColorText(Color color){
            text.setFill(color);
        }
        public void drawX(){
            text.setText("X");
            content = Seed.X;
        }
        public void drawO(){
            text.setText("O");
            content = Seed.O;
        }
        public void drawEmpty(){
            text.setText(null);
            content = Seed.EMPTY;
        }
        public void setIsChecked(boolean val){
            isChecked = val;
        }
        public boolean getIsChecked(){
            return isChecked;
        }
        public Seed getContent(){
            return content;
        }
    }

