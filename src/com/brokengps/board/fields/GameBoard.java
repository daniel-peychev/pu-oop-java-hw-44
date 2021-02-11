package com.brokengps.board.fields;

import com.brokengps.board.fields.Main;
import com.brokengps.board.fields.coordinates.Coordinates;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

public class GameBoard extends JFrame implements MouseListener {
    ArrayList<Piece> pieces = new ArrayList<Piece>();
    Coordinates currenPosition;

    public GameBoard() {
        this.setSize(800, 800);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        generateFields();

        //this.addMouseListener();
        addMouseListener(this);
        Graphics gr = this.getGraphics();
    }

    void printBoard(Graphics gr){
        // Render frames
        gr.setColor(Color.gray);
        // Render vertical
        for (int i = 0; i < 9; i++){
            gr.drawLine(Piece.marginTop+ i * Piece.cellSize,
                    Piece.marginTop ,
                    Piece.marginLeft + i * Piece.cellSize,
                    Piece.marginTop + 8 * Piece.cellSize);
        }

        // Render horizontal
        for (int i = 0; i < 9; i++){
            gr.drawLine(Piece.marginTop,
                    Piece.marginTop + i * Piece.cellSize,
                    Piece.marginLeft + 8 * Piece.cellSize,
                    Piece.marginTop + i * Piece.cellSize);
        }
    }

    void generateFields(){
        //generate startPoint
        StartPoint sp = new StartPoint();
        pieces.add(sp);
        currenPosition = new Coordinates(sp.getRow(), sp.getCol());

        // generate Gps Points
        GpsPoint gpsPointTrue = new GpsPoint(this, true);
        for (int i = 0 ; i < 7; i++){
            GpsPoint gpsPointFalse = new GpsPoint(this, false);
        }

        // generate imassable Points
        for (int i = 0; i < 5; i++){
            ImpassablePoint impassablePoint = new ImpassablePoint(this);
        }

        // generate uncharted Points
        for (int i = 0; i < 50; i++){
            UnchartedPoint uncharted = new UnchartedPoint(this);
        }
    }

    boolean isFieldEmpty(int row, int col){
        for(Piece p :pieces){
            if (p.getRow() == row && p.getCol() == col){
                return false;
            }
        }

        return true;
    }

    boolean isFieldEmpty(Coordinates coordinates){
        return isFieldEmpty(coordinates.getRow(), coordinates.getCol());
    }

    @Override
    public void paint(Graphics gr){
        super.paint(gr);

        for (Piece p : pieces){
            p.render(gr);
        }

        printBoard(gr);
    }

    private Coordinates getCellFromMouseCoordinates(int x, int y){
        x = (x - Piece.marginLeft) / Piece.cellSize;
        y = (y - Piece.marginTop) / Piece.cellSize;
        return new Coordinates(y, x);
    }

    private boolean isMoveAllowed(Coordinates moveTo){
        if (moveTo.getRow() < 0 ||
                moveTo.getRow() > 7 ||
                moveTo.getCol() < 0 ||
                moveTo.getCol() > 7){
            return false;
        }

        int rowOld = currenPosition.getRow();
        int colOld = currenPosition.getCol();
        int rowNew = moveTo.getRow();
        int colNew = moveTo.getCol();

        // Move Up Down
        if (Math.abs(rowNew - rowOld) == 1 && colNew == colOld){
            return true;
        }

        // Move Left Right
        if (Math.abs(colNew - colOld) == 1 && rowNew == rowOld){
            return true;
        }

        return false;
    }

    private Piece getPieceByCoordinates(Coordinates coordinates){
        for(Piece p : pieces){
            if (p.getRow() == coordinates.getRow() && p.getCol() == coordinates.getCol()){
                return p;
            }
        }

        return null;
    }

    private int getIndexByCoordinates(Coordinates coordinates){
        for(int i = 0; i < pieces.size(); i++){
            if (pieces.get(i).getRow() == coordinates.getRow() && pieces.get(i).getCol() == coordinates.getCol()){
                return i;
            }
        }

        return -1;
    }

    private void win(){

    }

    private boolean checkDeadEnd(Coordinates coordinates){
        Coordinates up = coordinates; up.setRow(coordinates.getRow() - 1);
        Coordinates down = coordinates; down.setRow(coordinates.getRow() + 1);
        Coordinates left = coordinates; left.setCol(coordinates.getCol() - 1);
        Coordinates right = coordinates; right.setCol(coordinates.getCol() + 1);
        if (getPieceByCoordinates(up).getClass().getSimpleName() == "ImpassablePoint" ||
                up.getRow() < 1 ||
                getPieceByCoordinates(up).getClass().getSimpleName() == "StartPoint" &&
                        getPieceByCoordinates(down).getClass().getSimpleName() == "ImpassablePoint" ||
                down.getRow() > 7 ||
                getPieceByCoordinates(down).getClass().getSimpleName() == "StartPoint" &&
                        getPieceByCoordinates(right).getClass().getSimpleName() == "ImpassablePoint" ||
                right.getCol() > 7 ||
                getPieceByCoordinates(right).getClass().getSimpleName() == "StartPoint" &&
                        getPieceByCoordinates(left).getClass().getSimpleName() == "ImpassablePoint" ||
                right.getCol() < 0 ||
                getPieceByCoordinates(left).getClass().getSimpleName() == "StartPoint"){
            return true;
        }
        return false;
    }

    private void cantWin(){
        JOptionPane.showMessageDialog(this, "Deadend!");
    }

    private void move(Coordinates moveTo){
        // check if move is allowed
        if (!isMoveAllowed(moveTo)){
            return;
        }

        // react according to piece type
        Piece newCell = getPieceByCoordinates(moveTo);
        String cellName = newCell.getClass().getSimpleName();
        if (cellName.equals("ImpassablePoint")){
            // can't pass
            return;
        } else if (cellName.equals("StartPoint")){
            // you've been there
            return;
        } else if (cellName.equals("UnchartedPoint")){

            // remove the uncharted piece
            pieces.remove(getIndexByCoordinates(moveTo));

            // Make new piece
            MarkedPoint mp = new MarkedPoint(this, moveTo);
            paint(super.getGraphics());
        } else if (cellName.equals("MarkedPoint")){
            // 80% move to - starting point + currentPoint = this / 20 impassable ponint
            Random rand = new Random();
            int chance = rand.nextInt(100) + 1;

            // JOptionPane.showMessageDialog(this, chance);
            if (chance >= 1 && chance <= 20){
                // Set the square to Impassable, Do not move
                pieces.remove(getIndexByCoordinates(moveTo));
                ImpassablePoint ip = new ImpassablePoint(this, moveTo);
                paint(super.getGraphics());
                if (checkDeadEnd(moveTo)){
                    cantWin();
                }
            } else {
                // Set the sqare to startPoint, move to this square
                pieces.remove(getIndexByCoordinates(moveTo));
                StartPoint sp = new StartPoint(this, moveTo);
                currenPosition = moveTo;
                paint(super.getGraphics());
                if (checkDeadEnd(moveTo)){
                    cantWin();
                }
            }
        } else if(cellName.equals("GpsPoint")){
            // check if baba iaga is there
            if (((GpsPoint)getPieceByCoordinates(moveTo)).isBabaIaga()){
                JOptionPane.showMessageDialog(this, "Baba Iaga found!!!");
                win();
            } else {
                // Move To square
                pieces.remove(getIndexByCoordinates(moveTo));
                StartPoint sp = new StartPoint(this, moveTo);
                currenPosition = moveTo;
                paint(super.getGraphics());
            }
        }

        //JOptionPane.showMessageDialog(this, newCell.getClass().getSimpleName());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Coordinates moveTo = getCellFromMouseCoordinates(e.getX(), e.getY());
        move(moveTo);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
