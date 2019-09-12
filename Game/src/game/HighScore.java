package game;

/**
 *  file: HighScore.java
 *  author: Team Goodbye Flash
 *  class: CS 2450 - Programming Graphical User Interfaces
 *
 * assignment: Swing Project 
 * date last modified: 9/11/19
 *
 * purpose: This class is the high score class that displays a high score GUI
 * that will be fully functional in the future (for the next project).
 *
 ***************************************************************
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import java.io.FileNotFoundException;

import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class HighScore extends JPanel implements ActionListener
{

    private JLabel[] highScoreArray = new JLabel[5]; //array of high scores where index 0 is highest.
    private JButton backButton = new JButton("Back"); //back button
    private Game game;

    private Scanner file;

    //Constructor, pass in game for reference.
    public HighScore(Game game) throws IOException
    {
        this.game = game;
        //initialize new Labels into array.

        File inputFile = new File("highscore.txt");

        try {
            if (!inputFile.exists()) {
                inputFile.createNewFile();

                FileWriter fW = new FileWriter(inputFile);

                for (int i = 0; i < highScoreArray.length; i++) {
                    highScoreArray[i] = new JLabel("ABC 000000");
                    fW.write("ABC 000000\n");
                }

                fW.flush();
                fW.close();
            } else {
                file = new Scanner(inputFile);

                for (int i = 0; i < highScoreArray.length; i++) {
                    highScoreArray[i] = new JLabel(file.nextLine());
                }
            }
        }
        catch(FileNotFoundException e){
            e.getMessage();
        }

        if (!inputFile.exists())
        {
            inputFile.createNewFile();

            FileWriter fW = new FileWriter(inputFile);

            for (int i = 0; i < highScoreArray.length; i++)
            {
                highScoreArray[i] = new JLabel("ABC 000000");
                fW.write("ABC 000000\n");
            }

            fW.flush();
            fW.close();
        }
        else
        {
            file = new Scanner(inputFile);

            for (int i = 0; i < highScoreArray.length; i++)
            {
                highScoreArray[i] = new JLabel(file.nextLine());
            }
        }


        //"Highscore" Label.
        JLabel hs = new JLabel("High Score");
        //container for white space and highscore label
        Container c1 = new Container();

        //setting the LayoutManager as box so that it appears as a list.
        c1.setLayout(new BoxLayout(c1, BoxLayout.PAGE_AXIS));

        //create whitespace between Panel and Label
        c1.add(Box.createRigidArea(new Dimension(0, 50)));
        c1.add(hs);

        //create whitespace between Label and HighScore Labels
        c1.add(Box.createRigidArea(new Dimension(0, 40)));

        //adds all highscores into the container.
        for (int i = 0; i < highScoreArray.length; i++)
        {
            c1.add(highScoreArray[i]);
        }

        //set the font for all the labels
        hs.setFont(new Font("Arial", Font.BOLD, 32));
        for (int i = 0; i < highScoreArray.length; i++)
        {
            highScoreArray[i].setFont(new Font("Arial", Font.BOLD, 16));
        }

        //listen for click
        backButton.addActionListener(this);
        backButton.setToolTipText("Main Menu");

        //create whitespace between back button and high scores.
        c1.add(Box.createRigidArea(new Dimension(0, 40)));
        c1.add(backButton);

        //add the container into the panel.
        this.add(c1);
    }

    //override method that allows the user to back into the menu.
    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object o = e.getSource();
        JButton b = null;
        String buttonText = "";

        if (o instanceof JButton)
        {
            b = (JButton) o;
        }

        if (b != null)
        {
            if (b.getText().equalsIgnoreCase("back"))
            {
                game.frame.getContentPane().setVisible(false);
                game.frame.getContentPane().remove(this);
                game.frame.add(new Menu(game));
                game.frame.getContentPane().setVisible(true);
            }
        }
    }
    public boolean isHighScore(int x)
    {
        boolean higher = false;
        String line;
        String[] splitLine;

        for (int i = 0; i < highScoreArray.length; i++)
        {
            line = highScoreArray[i].getText();
            splitLine = line.split(" ");
            if (x > Integer.parseInt(splitLine[1]))
            {
                higher = true;
                break;
            }
        }
        return higher;
    }

    public void updateHighScore(int x, String name) throws IOException
    {
        boolean higher = false;
        int position = 0;

        String line;
        String[] splitLine;

        for (int i = 0; i < highScoreArray.length; i++)
        {
            line = highScoreArray[i].getText();
            splitLine = line.split(" ");
            if (x > Integer.parseInt(splitLine[1]))
            {
                higher = true;
                position = i;
                break;
            }
        }

        if (higher)
        {
            JLabel tempJ;
            JLabel newScore = new JLabel(name + " " + x);

            for (int i = position; i < highScoreArray.length; i++)
            {
                tempJ = highScoreArray[i];
                highScoreArray[i] = newScore;
                newScore = tempJ;
            }

            FileWriter fW = new FileWriter(new File("highscore.txt"));

            for (int i = 0; i < highScoreArray.length; i++)
            {
                fW.write(highScoreArray[i].getText() + "\n");
            }

            fW.flush();
            fW.close();
         }
    }
}
