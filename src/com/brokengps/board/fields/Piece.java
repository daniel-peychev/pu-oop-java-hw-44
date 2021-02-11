package com.brokengps.board.fields;

import com.brokengps.board.fields.coordinates.Coordinates;

import java.awt.*;

public abstract class Piece {
    static int marginTop = 50;
    static int marginLeft = 50;
    static int cellSize = 50;

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

    int col;
    int row;

    protected Piece(int row, int col){
        this.row = row;
        this.col = col;
    }

    protected Piece(GameBoard gb){
        // generate GPS Point

        Coordinates coordinates;
        do{
            coordinates = Coordinates.genCoordinates();
        } while (!gb.isFieldEmpty(coordinates));

        setRow(coordinates.getRow());
        setCol(coordinates.getCol());

        gb.pieces.add(this);
    }

    protected Piece(GameBoard gb, Coordinates coordinates){
        setRow(coordinates.getRow());
        setCol(coordinates.getCol());

        gb.pieces.add(this);
    }

    protected Piece(){

    }


//    public void setCoordinates(CellCoordinates cell){
//        setRow(cell.getRow());
//        setCol(cell.getCol());
//    }

    public abstract void render(Graphics gr);
}
