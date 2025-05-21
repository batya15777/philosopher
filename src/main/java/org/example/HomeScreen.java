package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class HomeScreen extends JPanel {

    private Image image;
    private Fork[] forks;
    private Philosopher[] philosophers;
    private JButton[] buttons;

    public HomeScreen(JFrame frame) {

        this.image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/philosophers.PNG"))).getImage();

        this.forks = new Fork[5];
        this.forks[0] = new Fork(0, 660, 536);
        this.forks[1] = new Fork(1, 580, 390);
        this.forks[2] = new Fork(2, 450, 600);
        this.forks[3] = new Fork(3, 200, 550);
        this.forks[4] = new Fork(4, 320, 380);

        Waiter waiter = new Waiter();
        this.philosophers = new Philosopher[]{
                new Philosopher("John Lock", forks[0], forks[1], waiter, 400, 300),
                new Philosopher("Plato", forks[1], forks[2], waiter, 100, 700),
                new Philosopher("Rey", forks[2], forks[3], waiter, 100, 350),
                new Philosopher("Shon", forks[3], forks[4], waiter, 670, 700),
                new Philosopher("Enrico", forks[4], forks[0], waiter, 670, 350)
        };

        this.buttons = new JButton[philosophers.length];
        for (int i = 0; i < philosophers.length; i++) {
            int index = i;
            this.buttons[i] = new JButton("PAUSE");
            this.buttons[i].setBounds(philosophers[i].getTextX() + 35, philosophers[i].getTextY() - 40, 100, 25);
            this.buttons[i].addActionListener(e -> {
                Philosopher p = philosophers[index];
                if (!p.isPaused()) {
                    p.pause();
                    this.buttons[index].setText("▶ START");
                } else {
                    // אם מושהה → נפעיל ונחזיר טקסט
                    p.resume();
                    this.buttons[index].setText("⏸ PAUSE");
                }
            });
            this.setLayout(null);
            this.add(this.buttons[i]);

        }
        new Thread(() -> {
            while (true) {
                for (Philosopher p : this.philosophers) {
                    System.out.println(p);
                }
                Utils.sleep(1000);
            }
        }).start();
        new Thread(() -> {
            while (true) {
                repaint();
                Utils.sleep(500);
            }
        }).start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.image, 0, 0, getWidth(), getHeight(), this);
        if (this.forks != null) {
            for (Fork fork : this.forks) {
                fork.draw(g);
            }
        }
        if (this.philosophers != null) {
            g.setFont(new Font("Arial", Font.BOLD, 16));
            for (Philosopher p : this.philosophers) {
                String status = switch (p.getStatus()) {
                    case Philosopher.THINKING -> "THINKING";
                    case Philosopher.EATING -> "EATING";
                    case Philosopher.WAITING_FOR_FORK_1 -> "WAITING FOR FORK 1";
                    case Philosopher.WAITING_FOR_FORK_2 -> "WAITING FOR FOR 2";
                    default -> "";
                };
                g.setColor(p.getStatus() == Philosopher.EATING ? Color.RED : Color.BLACK);
                g.drawString("STATUS :" + status + " " + " X " + p.getEatingCount(), p.getTextX(), p.getTextY());
//            לשעם תמונה של צלחת אוכל...
//            Image scoreIcon = new ImageIcon(getClass().getResource("/score.png")).getImage();
//            g.drawImage(scoreIcon,868,8,30,30,this);
                if (p.getStatus() == Philosopher.EATING) {
                    g.setColor(Color.RED);
                    g.fillOval(p.getTextX() - 25, p.getTextY() - 40, 20, 20); // עיגול מעל הראש
                }
            }
        }

    }
}
