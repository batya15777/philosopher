package org.example;

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
                + " Philosopher "
                + this.name
                + " is currently "
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

}
