package com.brokengps.board.fields;

import com.brokengps.board.fields.coordinates.Coordinates;

import java.awt.*;
import java.util.Random;

public class StartPoint extends Piece {
    public StartPoint(){
        // generate position
        Random random = new Random();
        int startPosition = random.nextInt(4) + 1;
        switch(startPosition){
            case 1:
                setRow(0);
                setCol(0);
                break;
            case 2:
                setRow(0);
                setCol(7);
                break;
            case 3:
                setRow(7);
                setCol(0);
                break;
            case 4:
                setRow(7);
                setCol(7);
                break;
        }
    }

    public StartPoint(GameBoard gb, Coordinates coordinates){
        super(gb, coordinates);
    }

    @Override
    public void render(Graphics gr) {
        gr.setColor(Color.yellow);
        int x = marginLeft + col * cellSize;
        int y = marginTop + row * cellSize;

        gr.fillRect(x, y, cellSize, cellSize);
    }
}
