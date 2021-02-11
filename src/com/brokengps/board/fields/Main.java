package com.brokengps.board.fields;

import com.brokengps.board.fields.GameBoard;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard();
        MyFrame board = new MyFrame();
    }
    public static class MyFrame extends JFrame {
        public MyFrame(){
            this.setSize(400,200);
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            this.setVisible(true);
            this.setTitle("Прозорец");
            JLabel label1 = new JLabel("Край на играта! Опитай пак.");
            add(label1);

            JButton b=new JButton("Reset");
            b.setBounds(40,100,90,25);
            label1.add(b);
            label1.setSize(400,400);
            label1.setLayout(null);
            label1.setVisible(true);

        }
    }
}

