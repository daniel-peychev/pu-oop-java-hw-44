package com.brokengps.board.fields;

import com.brokengps.board.fields.coordinates.Coordinates;

import java.awt.*;

public class ImpassablePoint extends Piece {

    public ImpassablePoint(GameBoard gb){
        super(gb);
    }

    public ImpassablePoint(GameBoard gb, Coordinates coordinates){
        super(gb, coordinates);
    }

    @Override
    public void render(Graphics gr) {
        gr.setColor(Color.blue);
        int x = marginLeft + col * cellSize;
        int y = marginTop + row * cellSize;

        gr.fillRect(x, y, cellSize, cellSize);
    }
}
