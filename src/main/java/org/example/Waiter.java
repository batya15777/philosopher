package org.example;

public class Waiter {

    private int currentNumber;
    private final int MAX_EATING = 4;

    //    מחלקה שמייצגת המתנה של הטרדים
    public synchronized void askingForPermission() {
        while (currentNumber >= MAX_EATING) {
            try {
                wait();

            } catch (InterruptedException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
        currentNumber++;
    }

    //    מחלקה שמייצגת את שחרור ההמתנה הטרדים
    public synchronized void releasesPermission() {
        currentNumber--;
        notifyAll();
    }


}
