package com.company;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class GameForm {
    private JPanel Panel1;

    public JPanel getPanel1() {
        return Panel1;
    }

    private JLabel letter1;
    private JLabel letter2;
    private JLabel letter3;
    private JLabel letter4;
    private JLabel letter5;
    private JLabel letter6;
    private JLabel letter7;
    private JTextField textField1;
    private JLabel scoreLabel;
    private JLabel scoreLabel2;
    private JLabel timer;
    boolean gameEnd = false;

    private String dictionaryPath = "C:\\Users\\prith\\Anagrams 2\\Anagrams 2\\engmix.txt";

    public GameForm() throws FileNotFoundException {
        ArrayList<String> letters = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"));
        ArrayList<String> selected = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            selected.add(letters.get((int) (Math.random()*26)));
        }

        letter1.setText(selected.get(0).toUpperCase());
        letter2.setText(selected.get(1).toUpperCase());
        letter4.setText(selected.get(3).toUpperCase());
        letter5.setText(selected.get(4).toUpperCase());
        letter6.setText(selected.get(5).toUpperCase());
        letter7.setText(selected.get(6).toUpperCase());
        timer.setText("60");

        if (selected.contains("a") || selected.contains("e") || selected.contains("i") || selected.contains("o") || selected.contains("u")) {
            letter3.setText(selected.get(2).toUpperCase());
        }
        else {
            letter3.setText("E");
        }
        FileReader y = new FileReader(dictionaryPath);
        Scanner sc = new Scanner(y);
        ArrayList<String> dict = new ArrayList<>();
        ArrayList<String> entered = new ArrayList<>();
        while (sc.hasNext()){
            dict.add(sc.next());
        }
        final int[] score = {0};
        final String[] x = {""};
        textField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(timer.getText().equals("0")) return;
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    x[0] = textField1.getText();
                    System.out.println(textField1.getText());
                    boolean uses = true;

                    HashMap<String, Integer> letterCountsInSelected = new HashMap<>();
                    HashMap<String, Integer> letterCountsInEntered = new HashMap<>();
                    for (int i = 0; i < x[0].length(); i++) {
                        String currLetter = String.valueOf(x[0].charAt(i));
                        System.out.println("curr letter: " + currLetter);
                        if(!letterCountsInEntered.containsKey(currLetter)) letterCountsInEntered.put(currLetter,1);
                        else{
                            letterCountsInEntered.put(currLetter, letterCountsInEntered.get(currLetter)+1);
                        }
                        if(selected.contains(currLetter)){

                        }else {
                            uses = false;
                            textField1.setBackground(Color.RED);
                            try {
                                synchronized (textField1) {
                                    textField1.wait(500);
                                }

                            } catch (InterruptedException interruptedException) {
                                interruptedException.printStackTrace();
                            }
                            textField1.setText("");
                            break;
                        }
                    }

                    for (String letter : selected) {
                        if(!letterCountsInSelected.containsKey(letter)){letterCountsInSelected.put(letter,1);}
                        else{
                            letterCountsInSelected.put(letter, letterCountsInSelected.get(letter)+1);
                        }
                    }
                    System.out.println(letterCountsInEntered.toString());
                    System.out.println(letterCountsInSelected.toString());
                    boolean wordCountsAreFine = true;
                    if(uses){
                        for(String letter : letterCountsInEntered.keySet()){
                            if(letterCountsInEntered.get(letter) > letterCountsInSelected.get(letter)) wordCountsAreFine=false;
                        }
                    }
                    if (uses && dict.contains(x[0]) && !(entered.contains(x[0])) && wordCountsAreFine) {
                        entered.add(x[0]);
                        textField1.setBackground(Color.GREEN);
                        try{
                            synchronized (textField1) { textField1.wait(500);}
                        } catch (InterruptedException interruptedException) { interruptedException.printStackTrace(); }
                        score[0] = score[0] + x[0].length();
                        scoreLabel2.setText(String.valueOf(score[0]));
                        textField1.setText("");
                    } else {
                        textField1.setBackground(Color.RED);
                        try {
                            synchronized (textField1) {
                                textField1.wait(500);
                            }
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                        textField1.setText("");
                    }
                }
            }
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    try{
                        synchronized (textField1) { textField1.wait(1000);}
                    } catch (InterruptedException interruptedException) { interruptedException.printStackTrace(); }
                    textField1.setBackground(Color.WHITE);
                }
            }
        });

        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currTime = Integer.parseInt(timer.getText());
                if (gameEnd){
                    return;
                }
                if(currTime == 0){
                    gameEnd = true;
                    JFrame frame = new JFrame("Anagrams");
                    frame.setContentPane(new RestartForm().getPanel1());
                    frame.setSize(800,600);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setLocation(200,100);
                    frame.setVisible(true);
                }else{
                    timer.setText(String.valueOf(currTime-1));
                }
            }
        };
        Timer time = new Timer(1000, al);
        time.setRepeats(true);
        time.start();


    }
}
