/**
 * file: Game.java
 * author: Team Goodbye Flash
 * class: CS 2450 - Programming Graphical User Interfaces
 *
 * assignment: Swing Project 
 * date last modified: 9/11/19
 *
 * purpose: This is the main method which ties all other panels into
 * one frame.
 *
 ****************************************************************/

import javax.swing.*;
import java.awt.*;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class Game{

    final int FINAL_WIDTH = 600; //frame dimensions
    final int FINAL_HEIGHT = 400;

    JFrame frame;
    MyPanel intro;
    Credits credits;
    Menu menu;
    HighScore hs;
    ScoreScreen ss;
    Hangman hg;
    
    public enum STATE{ //allows access to each panel
        Intro,
    }

    public static STATE gameState = STATE.Intro; //starts game in intro


    public Game() throws IOException { //initializes each panel upon creating the frame

        frame = new JFrame("CS2450 Project");
        intro = new MyPanel(this);
        hs = new HighScore(this);
        hg = new Hangman(this);
        menu = new Menu(this);
        credits = new Credits(this);



        frame.setPreferredSize(new Dimension(FINAL_WIDTH, FINAL_HEIGHT));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.add(intro);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //Universal KeyEvent detector, used for ESC and F1 Keys
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addKeyEventDispatcher(new KeyEventDispatcher() {
                    @Override
                    public boolean dispatchKeyEvent(KeyEvent e) {
                        if(KeyEvent.KEY_PRESSED == e.getID()){
                            if(e.getKeyCode()==KeyEvent.VK_F1)
                            JOptionPane.showMessageDialog(frame,
                                    "                 " +
                                            "Created By:" + "\n" +
                                            "Nghi Tran (011806600)" + "\n" +
                                            "Robert Delfin (011851034)" + "\n" +
                                            "Louis Guan (0117249806)" + "\n" +
                                            "CS2450 Project" + "\n" +
                                            "Fall 2019");

                        }
                        if(KeyEvent.KEY_PRESSED == e.getID()){
                            if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
                                System.exit(0);
                        }
                        return false;
                    }
                });

        if(gameState == STATE.Intro) { //create game state with intro screen

            try {
                TimeUnit.SECONDS.sleep(3); //hold title for 3 second
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }

            frame.getContentPane().setVisible(false);
            frame.getContentPane().remove(intro);
            frame.add(menu); //start
            frame.getContentPane().setVisible(true);

        }


    }


    public static void main(String[] args) throws IOException {
        new Game();
    } //run game
}