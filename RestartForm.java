package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class RestartForm {
    private JPanel Panel1;
    private JButton restartButton;

    public RestartForm() {
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Anagrams");
                try {
                    frame.setContentPane(new GameForm().getPanel1());
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
                frame.setSize(800,600);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocation(200,100);
                frame.setVisible(true);
            }
        });
    }

    public JPanel getPanel1() {
        return Panel1;
    }
}
