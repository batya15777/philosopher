package org.example;

import javax.swing.*;
import java.awt.*;

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
        imageOriginal = new ImageIcon(getClass().getResource("/fork.png")).getImage();
        heldByImage = new ImageIcon(getClass().getResource("/redFork.png")).getImage();

    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        switch (number) {
            case 0 -> g2d.rotate(Math.toRadians(-90), x + width / 2, y + height / 2); // אופקי שמאלה
            case 1 -> g2d.rotate(Math.toRadians(575), x + width / 2, y + height / 2); // בין שמאלה למעלה
            case 2 -> g2d.rotate(Math.toRadians(0), x + width / 2, y + height / 2);   // ישר למעלה
            case 3 -> g2d.rotate(Math.toRadians(45), x + width / 2, y + height / 2);  // בין ימינה ללמעלה
            case 4 -> g2d.rotate(Math.toRadians(100), x + width / 2, y + height / 2);  // ימינ

        }
        Image currentImage = (heldBy != null && heldBy.getStatus() == Philosopher.EATING) ? heldByImage : imageOriginal;
        g2d.drawImage(currentImage, x, y, width, height, null);
        g2d.dispose();

    }

    public Philosopher getHeldBy() {
        return heldBy;
    }

    public void setHeldBy(Philosopher heldBy) {
        this.heldBy = heldBy;
    }

    //        פונקצית עזר לשחרור המזלגות
    public synchronized void release() {
        heldBy = null;
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
            return "Fork " + this.number
                    + " is not currently held by anyone";
        } else {
            return "Fork " + this.number +
                    " is currently held by " +
                    this.heldBy.getName();
        }
    }


}






