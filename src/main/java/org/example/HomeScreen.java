package org.example;

import javax.swing.*;
import java.awt.*;

public class HomeScreen extends JPanel {
 private Image image;




HomeScreen(JFrame frame){

    image = new ImageIcon(getClass().getResource("/9D9AF0BD-B60C-44EB-9E8B-1E5B78ECF581.PNG")).getImage();



    Fork fork1 = new Fork(1);
    Fork fork2 = new Fork(2);
    Fork fork3 = new Fork(3);
    Fork fork4 = new Fork(4);
    Fork fork5 = new Fork(5);



    Waiter waiter = new Waiter();

    Philosopher philosopher1 = new Philosopher("John Lock", fork1, fork2, waiter);
    Philosopher philosopher2 = new Philosopher("Plato", fork2, fork3, waiter);
    Philosopher philosopher3 = new Philosopher("Rey", fork3, fork4, waiter);
    Philosopher philosopher4 = new Philosopher("Shon", fork4, fork5, waiter);
    Philosopher philosopher5 = new Philosopher("Enrico", fork5, fork1, waiter);

      new Thread(() -> {
        while (true) {
            System.out.println(philosopher1);
            System.out.println(philosopher2);
            System.out.println(philosopher3);
            System.out.println(philosopher4);
            System.out.println(philosopher5);







            Utils.sleep(1000);
        }
    }).start();









}
public void paintComponent(Graphics g){
    super.paintComponent(g);
    g.drawImage(image,0,0, getWidth(),getHeight(),this);




}




}
