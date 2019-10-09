/***************************************************************
 * file: ButtonGame.java
 * author: Team Goodbye Flash
 * class: CS 2450 - Programming Graphical User Interfaces
 *
 * assignment: Swing Project v1.2
 * date last modified: 9/24/19
 *
 * purpose: This class runs the button game panel
 *
 ****************************************************************/

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.IOException;
import java.util.*;
import java.util.List;


public class ButtonGame extends JPanel implements ActionListener
{

    private Game game;
    private HighScore hs;
    private JLabel clockLabel;
    private JLabel colorLabel;
    private JButton[] randomCircles;
    private Random r;
    private int score;
    private int tries;

    //constructor, accepts game for reference
    public ButtonGame(Game game, int score)
    {
        this.score = score;
        this.game = game;
        tries = 5;
        loadGUI();
        clock();
    }

    //override method that allows the user to back into the menu.
    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        JButton b = null;
        String buttonText = "";

        if(o instanceof JButton) {
            b = (JButton) o;
        }

        if(b!=null){
            if(b.getBackground().equals(colorLabel.getForeground())){

                score += 100;
                game.hg.setScore(score);
                tries--;
                generateGame();

                if(tries == 0){
                    game.frame.getContentPane().setVisible(false);
                    game.frame.getContentPane().remove(this);
                    game.frame.add(new Sudoku(game, score));
                    game.frame.getContentPane().setVisible(true);
                }

            }

            else{

                tries--;
                generateGame();

                if(tries == 0){
                    game.frame.getContentPane().setVisible(false);
                    game.frame.getContentPane().remove(this);
                    game.frame.add(new Sudoku(game, score));
                    game.frame.getContentPane().setVisible(true);
                }
            }
        }

    }

    public void clock(){ //clock that runs throughout program
        Thread clock = new Thread(){
            public void run(){
                try{
                    for(;;) {
                        Calendar cal = new GregorianCalendar();
                        int day = cal.get(Calendar.DAY_OF_MONTH);
                        int month = cal.get(Calendar.MONTH)+1;
                        int year = cal.get(Calendar.YEAR);

                        int second = cal.get(Calendar.SECOND);
                        int minute = cal.get(Calendar.MINUTE);
                        int hour = cal.get(Calendar.HOUR);

                            clockLabel.setText("Time " + hour + ":" + minute + ":" + second + "      Date " + month + "/" + day + "/" + year);
                            sleep(1000);

                    }
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        clock.start();
    }

    public void loadGUI(){
        this.game = game;
        colorLabel = new JLabel("");
        randomCircles = new JButton[5];
        for (int i = 0;i<randomCircles.length;i++){
            randomCircles[i] = new JButton("");

        }
        clockLabel = new JLabel("");

        setLayout(null);

        for(int i = 0;i<randomCircles.length;i++) {
            randomCircles[i].addActionListener(this);
            add(randomCircles[i]);
        }

        clockLabel.setBounds(400,2,200,20);
        colorLabel.setBounds(245,40, 200, 40);
        colorLabel.setFont(new Font("Arial", Font.BOLD, 32));
        add(clockLabel);
        add(colorLabel);

        generateGame();






    }

    public void generateGame(){

        r = new Random();
        Color[] colors = {Color.RED,Color.YELLOW,Color.BLUE,Color.GREEN,Color.MAGENTA};

        int random = r.nextInt(5);
        int randomColor = r.nextInt(5);


        if (random == 4) {
            colorLabel.setText("Red");
            colorLabel.setForeground(colors[randomColor]);
        }
        if (random == 3) {
            colorLabel.setText("Blue");
            colorLabel.setForeground(colors[randomColor]);
        }
        if (random == 2) {
            colorLabel.setText("Yellow");
            colorLabel.setForeground(colors[randomColor]);
        }
        if (random == 1) {
            colorLabel.setText("Magenta");
            colorLabel.setForeground(colors[randomColor]);
        }
        if (random == 0) {
            colorLabel.setText("Green");
            colorLabel.setForeground(colors[randomColor]);
        }

        List<Integer> l = Arrays.asList(0, 1, 2, 3, 4);
        Collections.shuffle(l);


        int xPos1 = 75;
        int yPos1 = 90;
        int xPos2 = 68;
        int yPos2 = 240;
        int xPos3 = 260;
        int yPos3 = 160;
        int xPos4 = 435;
        int yPos4 = 100;
        int xPos5 = 420;
        int yPos5 = 230;



        randomCircles[0].setBounds(xPos1,yPos1,100,100);
        randomCircles[0].setBackground(colors[l.get(0)]);
        randomCircles[0].setOpaque(true);
        randomCircles[0].setToolTipText("Select");
        randomCircles[1].setBounds(xPos2,yPos2,100,100);
        randomCircles[1].setBackground(colors[l.get(1)]);
        randomCircles[1].setOpaque(true);
        randomCircles[1].setToolTipText("Select");
        randomCircles[2].setBounds(xPos3,yPos3,100,100);
        randomCircles[2].setBackground(colors[l.get(2)]);
        randomCircles[2].setOpaque(true);
        randomCircles[2].setToolTipText("Select");
        randomCircles[3].setBounds(xPos4,yPos4,100,100);
        randomCircles[3].setBackground(colors[l.get(3)]);
        randomCircles[3].setOpaque(true);
        randomCircles[3].setToolTipText("Select");
        randomCircles[4].setBounds(xPos5,yPos5,100,100);
        randomCircles[4].setBackground(colors[l.get(4)]);
        randomCircles[4].setOpaque(true);
        randomCircles[4].setToolTipText("Select");
    }
}