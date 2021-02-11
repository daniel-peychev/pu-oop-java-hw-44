package com.brokengps.board.fields;

import com.brokengps.board.fields.coordinates.Coordinates;

import java.awt.*;

public class UnchartedPoint extends Piece {
    public UnchartedPoint(GameBoard gb){
        super(gb);
    }

    @Override
    public void render(Graphics gr) {
        gr.setColor(Color.pink);
        int x = marginLeft + col * cellSize;
        int y = marginTop + row * cellSize;

        gr.fillRect(x, y, cellSize, cellSize);
    }
}
