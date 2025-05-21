package org.example;

import javax.swing.*;

public class Main {

    public static int WIDTH = 1000;
    public static int HEIGHT = 800;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            JFrame jFrame = new JFrame();
            jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            jFrame.setResizable(false);
            jFrame.setSize(WIDTH,HEIGHT);
            jFrame.setLocationRelativeTo(null);
            HomeScreen homeScreen = new HomeScreen(jFrame);
            jFrame.add(homeScreen);
            jFrame.setVisible(true);
        });
    }
}