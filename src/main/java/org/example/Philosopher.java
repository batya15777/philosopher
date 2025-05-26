package org.example;

import java.awt.Color;
import java.awt.Graphics;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Philosopher {

    public static final int THINKING = 1;
    public static final int WAITING_FOR_FORK_1 = 2;
    public static final int WAITING_FOR_FORK_2 = 3;
    public static final int EATING = 4;

    private String name;
    private int status;
    private int eatingCount;
    private Fork rightFork;
    private Fork leftFork;
    private Waiter waiter;
    private int textX;
    private int textY;
    private boolean isPaused = false;


    public Philosopher(String name, Fork rightFork, Fork leftFork, Waiter waiter, int textX, int textY) {
        this.name = name;
        this.status = THINKING;
        this.eatingCount = 0;
        this.rightFork = rightFork;
        this.leftFork = leftFork;
        this.waiter = waiter;
        this.textX = textX;
        this.textY = textY;

        this.start();
    }

    private void start() {
        new Thread(() -> {
            Random random = new Random();
            while (true) {
                while (isPaused) {
                    Utils.sleep(100);
                }
                Utils.sleep(random.nextInt(5000));
                waiter.askingForPermission();
                this.status = WAITING_FOR_FORK_1;
                while (!rightFork.check(this)) {
                    Utils.sleep(100);
                }
                this.rightFork.setHeldBy(this);
                Utils.sleep(random.nextInt(2000));
                this.status = WAITING_FOR_FORK_2;
                while (!leftFork.check(this)) {
                    Utils.sleep(100);
                }
                this.leftFork.setHeldBy(this);
                this.status = EATING;
                Utils.sleep(random.nextInt(1000));
                this.eatingCount++;
                rightFork.release();
                leftFork.release();
                waiter.releasesPermission();
                this.status = THINKING;

            }
        }).start();
    }

    public void pause() {
        isPaused = true;
    }

    public void resume() {
        isPaused = false;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        String statusText =
                switch (this.status) {
            case THINKING -> "thinking ";
            case WAITING_FOR_FORK_1 -> "waiting for fork " + this.rightFork.getNumber();
            case WAITING_FOR_FORK_2 -> "waiting for fork " + this.leftFork.getNumber();
            case EATING -> "eating ";
            default -> "";
        };
        return new SimpleDateFormat("HH:mm:ss")
                .format(new Date())
                + ": "
                + this.name
                + " is "
                + statusText
                + " (total times he ate: "
                + this.eatingCount + ")";

    }

    public int getStatus() {
        return this.status;
    }

    public int getEatingCount() {
        return eatingCount;
    }

    public int getTextY() {
        return textY;
    }

    public int getTextX() {
        return textX;
    }

    public void drawStatusOrForks(Graphics g) {
        if (this.status == EATING) {
            g.setColor(Color.GREEN);
            g.fillOval(this.textX - 25, this.textY - 40, 20, 20);
        }
    }

    public void drawHeldForks(Graphics g, int buttonY) {
        if (this.status == EATING) {
            if (rightFork != null) {
                rightFork.draw(g, this.textX + 60, buttonY - 40);
            }
            // Draw left fork above the button
            if (leftFork != null) {
                leftFork.draw(g, this.textX - 40, buttonY - 40);
            }
        }
    }

}
