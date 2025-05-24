package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class HomeScreen extends JPanel {

    private Image image;
    private Fork[] forks;
    private Philosopher[] philosophers;
    private JButton[] buttons;

    public HomeScreen() {

        this.image = new ImageIcon(Objects.requireNonNull(getClass()
                .getResource("/philosophers.PNG"))).getImage();

        this.forks = new Fork[5];
        this.forks[0] = new Fork(0, 550, 400);
        this.forks[1] = new Fork(1, 300, 400);
        this.forks[2] = new Fork(2, 225, 525);
        this.forks[3] = new Fork(3, 450, 600);
        this.forks[4] = new Fork(4, 600, 525);

        Waiter waiter = new Waiter();
        this.philosophers = new Philosopher[]{
                new Philosopher("John Lock", forks[0], forks[1], waiter, 400, 300),
                new Philosopher("Rey", forks[1], forks[2], waiter, 100, 350),
                new Philosopher("Plato", forks[2], forks[3], waiter, 100, 700),
                new Philosopher("Shon", forks[3], forks[4], waiter, 670, 700),
                new Philosopher("Enrico", forks[4], forks[0], waiter, 670, 350)
        };

        this.buttons = new JButton[philosophers.length];
        for (int i = 0; i < philosophers.length; i++) {
            int index = i;
            this.buttons[i] = new JButton("PAUSE");
            this.buttons[i].setBounds(philosophers[i].getTextX() + 35
                    ,philosophers[i].getTextY() - 40, 100, 25);
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
                // Only draw forks at the table if not held by a philosopher who is eating
                if (fork == null || (fork.isHeld())) continue;
                fork.draw(g);
            }
        }
        if (this.philosophers != null) {
            g.setFont(new Font("Arial", Font.BOLD, 16));

            for (int i = 0; i < philosophers.length; i++) {
                Philosopher p = philosophers[i];
                String status = switch (p.getStatus()) {
                    case Philosopher.THINKING -> "THINKING";
                    case Philosopher.EATING -> "EATING";
                    case Philosopher.WAITING_FOR_FORK_1 -> "WAITING FOR FORK 1";
                    case Philosopher.WAITING_FOR_FORK_2 -> "WAITING FOR FOR 2";
                    default -> "";
                };

                String ateText = "Ate " + p.getEatingCount() + " times";
                String statusLine = "Status: " + status;

                int textX = p.getTextX();
                int textY = p.getTextY();
                FontMetrics fm = g.getFontMetrics();
                int ateWidth = fm.stringWidth(ateText);
                int statusWidth = fm.stringWidth(statusLine);

                int textWidth = Math.max(ateWidth, statusWidth);
                int textHeight = fm.getHeight();

                int padding = 6;
                int rectY = textY - fm.getAscent() - padding / 2;

                g.setColor(new Color(255, 255, 145));

                g.fillRoundRect(textX - padding, rectY, textWidth + 2 * padding, 2 * textHeight + padding, 10, 10);
                g.setColor(p.getStatus() == Philosopher.EATING ? Color.GREEN : Color.BLACK);
                g.drawString(ateText, textX, textY);
                g.drawString(statusLine, textX, textY + textHeight);
                p.drawStatusOrForks(g);
                int buttonY = buttons[i].getY();
                p.drawHeldForks(g, buttonY);
            }
        }

    }
}
