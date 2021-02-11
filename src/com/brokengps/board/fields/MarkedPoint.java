package com.brokengps.board.fields;

import com.brokengps.board.fields.coordinates.Coordinates;

import java.awt.*;

public class MarkedPoint extends Piece {
    public MarkedPoint(GameBoard gb, Coordinates coordinates){
        super(gb, coordinates);
    }

    @Override
    public void render(Graphics gr) {
        gr.setColor(Color.yellow);
        int x = marginLeft + col * cellSize;
        int y = marginTop + row * cellSize;

        gr.fillRect(x, y, cellSize, cellSize);
        gr.setColor(Color.red);
        // render questionmark
        gr.drawString("?", x + 15, y + 15);
    }
}
