package com.brokengps.board.fields;

import com.brokengps.board.fields.coordinates.Coordinates;

import java.awt.*;

public class GpsPoint extends Piece {
    boolean isBabaIaga;
    public GpsPoint(GameBoard gb, boolean isBabaIaga){
        super(gb);
        this.isBabaIaga = isBabaIaga;
    }
    @Override
    public void render(Graphics gr) {
        gr.setColor(Color.green);
        int x = marginLeft + col * cellSize;
        int y = marginTop + row * cellSize;

        gr.fillRect(x, y, cellSize, cellSize);
    }

    public boolean isBabaIaga() {
        return isBabaIaga;
    }

    public void setBabaIaga(boolean babaIaga) {
        isBabaIaga = babaIaga;
    }
}
