package com.brokengps.board.fields.coordinates;

import java.util.Random;

public class Coordinates {
    int col;
    int row;

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public Coordinates(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public static Coordinates genCoordinates(){
        Random rand = new Random();

        int row = rand.nextInt(8);
        int col = rand.nextInt(8);

        return  new Coordinates(row, col);
    }

    @Override
    public String toString(){
        return "(" + getRow() + ", " + getCol() + ")";
    }
}
