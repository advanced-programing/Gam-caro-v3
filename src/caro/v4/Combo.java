/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caro.v4;

/**
 *
 * @author USER
 */
public class Combo {
        public Cell[] cells;
        public Combo(Cell... cells) {
            this.cells = cells;
        }

        public boolean isComplete() {
            if (cells[0].getText().isEmpty())
                return false;

            return cells[0].getText().equals(cells[1].getText())
                    && cells[0].getText().equals(cells[2].getText());
        }
    }
