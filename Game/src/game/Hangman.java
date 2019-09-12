package game;

/**
 *  file: Hangman.java
 *  author: Team Goodbye Flash
 *  class: CS 2450 - Programming Graphical User Interfaces
 *
 * assignment: Swing Project 
 * date last modified: 9/11/19
 *
 * purpose: GUI for hangman game, consists of the score
 * and keeps track of the right and wrong guesses.
 ****************************************************************/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;


public class Hangman extends JPanel implements ActionListener{

    private static final long serialVersionUID = -3872551417773200878L;

    //declares all variables used

    private JButton[] buttons;
    private JButton skipButton;
    private JLabel[] underscoresLabel;
    private JLabel scoreLabel;
    private JLabel clockLabel;
    private Random r = new Random();
    private String[] words = {"ganon","cemetery","shrines","pharmacy","climbing"};
    private boolean[] guessed;
    private String word;
    private String choice;
    private int score=100;
    private int mistakes =0;
    private Game game;
    private HighScore hs;

    public Hangman(Game game){
        this.game = game;
        int randomNumber = r.nextInt(words.length);
        word = words[randomNumber];
        guessed = new boolean[word.length()];
        loadGUI();
        clock();
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

    public void loadGUI(){ //draws the screen with each underscore and hangman diagram

        int ascii = 65;
        underscoresLabel = new JLabel[word.length()];
        buttons = new JButton[26];
        clockLabel = new JLabel("");
        scoreLabel = new JLabel("Score: "+score);
        skipButton = new JButton("Skip");
        skipButton.addActionListener(this);
        skipButton.setToolTipText("Continue to next game");


        Container hold = new Container();


        hold.setLayout(new BoxLayout(hold,BoxLayout.PAGE_AXIS));
        hold.add(clockLabel);
        hold.add(scoreLabel);
        hold.add(Box.createRigidArea(new Dimension(50,220))); //30

        Container buttonContainer = new Container();
        Container wordContainer = new Container();
        buttonContainer.setLayout(new GridLayout(2,13,2,0));
        wordContainer.setLayout(new GridLayout(1,word.length()));


        hold.add(wordContainer);
        hold.add(Box.createRigidArea(new Dimension(50,20))); //30
        hold.add(buttonContainer);




        for (int i = 0;i<underscoresLabel.length;i++){
            underscoresLabel[i] = new JLabel("_");
            wordContainer.add(underscoresLabel[i]);
        }
        for(int i = 0;i<buttons.length;i++,ascii++) {

            buttons[i] = new JButton(Character.toString((char) ascii));
            buttons[i].setToolTipText("Select");
            buttons[i].setPreferredSize(new Dimension(30,30));
            buttons[i].setBorder(null);
            buttons[i].setFont(new Font("Arial", Font.BOLD, 10));
            buttons[i].addActionListener(this);
            buttonContainer.add(buttons[i]);

        }


        add(hold);
        add(skipButton);

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Font f = new Font("Arial", Font.BOLD, 32);
        Font f2 = new Font("Arial", Font.BOLD, 16);
        Graphics2D g2 = (Graphics2D)g;
        g2.setFont(f);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        g2.drawString("Hangman",0,25);

        //horizontal bar
        g2.fillRect(240, 70, 100, 5);


        //vertical bar
        g2.fillRect(240,70,5,160);

        //rope
        g2.fillRect(340,70,5,30);

        //horizontal bar
        g2.fillRect(120,230,320,5);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        JButton b = null;
        String buttonText = "";
        Graphics2D g2 = (Graphics2D)this.getGraphics();

        if(o instanceof JButton) {
            b = (JButton) o;
        }

        if( b!=null){ //allows access to the button variable and can determine what action to take
            setChoice(b.getText().toLowerCase());

            if (choice.equals("skip")){
                setScore(0);
                game.frame.getContentPane().setVisible(false);
                game.frame.getContentPane().remove(this);
                try {
					game.frame.add(new ScoreScreen(this.game, score));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                game.frame.getContentPane().setVisible(true);
            }

            if(mistakes == 0 && !word.contains(getChoice()))
            {
                g2.drawOval(332,100,20,20);
                mistakes++;
                score-=10;
                scoreLabel.setText("Score: "+Integer.toString(score));
                b.setVisible(false);
            }
            else if (mistakes == 1 && !word.contains(getChoice()))// draw body
            {
                g2.drawLine(342, 120, 342, 175);
                mistakes++;
                score-=10;
                scoreLabel.setText("Score: "+Integer.toString(score));
                b.setVisible(false);
            }
            else if (mistakes == 2 && !word.contains(getChoice())) {
                g2.drawLine(342, 120, 322, 160);
                mistakes++;
                score-=10;
                scoreLabel.setText("Score: "+Integer.toString(score));
                b.setVisible(false);
            }

            else if (mistakes == 3 && !word.contains(getChoice())) //draw right arm
            {
                g2.drawLine(342, 120, 362, 160);
                mistakes++;
                score-=10;
                scoreLabel.setText("Score: "+Integer.toString(score));
                b.setVisible(false);
            }

            else if (mistakes == 4 && !word.contains(getChoice())) //draw left leg
            {
                g2.drawLine(342, 175, 330, 220);
                mistakes++;
                score-=10;
                scoreLabel.setText("Score: "+Integer.toString(score));
                b.setVisible(false);
            }

            else if (mistakes == 5 && !word.contains(getChoice())) //draw right leg
            {
                g2.drawLine(342, 175, 353, 220);
                mistakes++;
                score-=10;
                scoreLabel.setText("Score: "+Integer.toString(score));
                b.setVisible(false);
            }

            else if (mistakes <6 && word.contains(getChoice())){

                for(int i =0;i<word.length();i++){
                    if(b.getText().toLowerCase().charAt(0) == word.charAt(i)){
                        underscoresLabel[i].setText(b.getText());
                        guessed[i] = true;
                    }
                }
                b.setVisible(false);

            }

            boolean end = true; //checks if all spaces have been filled or enough mistakes have been made to end

            for (int i = 0; i < guessed.length; i++)
            {
                if (!guessed[i])
                    end = false;
            }
            

            if(mistakes ==6 || end){
            	game.frame.getContentPane().setVisible(false);
                game.frame.getContentPane().remove(this);
                try {
					game.frame.add(new ScoreScreen(this.game, score));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                game.frame.getContentPane().setVisible(true);
           
            }
        }
    }

    public void setChoice(String s){
        choice = s;
    }

    public String getChoice(){
        return choice;
    }

    public int getScore(){
        return score;
    }
    public void setScore(int s){
        score = s;
    }
}