package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Fork {

    private int number;
    private Philosopher heldBy;
    private Image imageOriginal;
    private Image heldByImage;

    private int x;
    private int y;
    private final int width = 80;
    private final int height = 80;

    public int getNumber() {
        return number;
    }

    public Fork(int number, int x, int y) {
        this.number = number;
        this.heldBy = null;
        this.x = x;
        this.y = y;
        imageOriginal = new ImageIcon(Objects.requireNonNull(getClass()
                .getResource("/fork.png"))).getImage();
        heldByImage = new ImageIcon(Objects.requireNonNull(getClass()
                .getResource("/redFork.png"))).getImage();
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        switch (number) {
            case 0 -> g2d.rotate(Math.toRadians(220), x + (double) width / 2, y + (double) height / 2); // אופקי שמאלה
            case 1 -> g2d.rotate(Math.toRadians(110), x + (double) width / 2, y + (double) height / 2); // בין שמאלה למעלה
            case 2 -> g2d.rotate(Math.toRadians(65), x + (double) width / 2, y + (double) height / 2);   // ישר למעלה
            case 3 -> g2d.rotate(Math.toRadians(0), x + (double) width / 2, y + (double) height / 2);  // בין ימינה ללמעלה
            case 4 -> g2d.rotate(Math.toRadians(270), x + (double) width / 2, y + (double) height / 2);  // ימינ
        }

        Image currentImage = (heldBy != null && heldBy.getStatus() == Philosopher.EATING) ? heldByImage : imageOriginal;
        g2d.drawImage(currentImage, x, y, width, height, null);
        g2d.dispose();
    }

    public void draw(Graphics g, Integer customX, Integer customY) {
        Graphics2D g2d = (Graphics2D) g.create();
        int drawX = (customX != null) ? customX : x;
        int drawY = (customY != null) ? customY : y;
        if (customX == null) {
            switch (number) {
                case 0 -> g2d.rotate(Math.toRadians(-90), x + (double) width / 2, y + (double) height / 2);
                case 1 -> g2d.rotate(Math.toRadians(575), x + (double) width / 2, y + (double) height / 2);
                case 2 -> g2d.rotate(Math.toRadians(0), x + (double) width / 2, y + (double) height / 2);
                case 3 -> g2d.rotate(Math.toRadians(45), x + (double) width / 2, y + (double) height / 2);
                case 4 -> g2d.rotate(Math.toRadians(100), x + (double) width / 2, y + (double) height / 2);
            }
        }
        Image currentImage = (heldBy != null && heldBy.getStatus() == Philosopher.EATING) ? heldByImage : imageOriginal;
        g2d.drawImage(currentImage, drawX, drawY, width, height, null);
        g2d.dispose();
    }

    public synchronized void setHeldBy(Philosopher heldBy) {
        this.heldBy = heldBy;
    }

    //        פונקצית עזר לשחרור המזלגות
    public synchronized void release() {
        heldBy = null;
    }

    public boolean isHeld() {
        return heldBy != null && heldBy.getStatus() == Philosopher.EATING;
    }

    public synchronized boolean check(Philosopher philosopher) {
        if (heldBy == null) {
            heldBy = philosopher;
            return true;
        }
        return false;
    }

    public String toString() {
        if (this.heldBy == null) {
            return "Fork "
                    + this.number
                    + " is not currently held by anyone";
        } else {
            return "Fork "
                    + this.number
                    + " is currently held by "
                    + this.heldBy.getName();
        }
    }


}

