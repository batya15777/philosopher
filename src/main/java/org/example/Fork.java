package org.example;

public class Fork {

        private int number;
        private Philosopher heldBy;

        public int getNumber() {
            return number;
        }

        public Fork (int number) {
            this.number = number;
            this.heldBy = null;
        }

        public Philosopher getHeldBy() {
            return heldBy;
        }

        public void setHeldBy(Philosopher heldBy) {
            this.heldBy = heldBy;
        }
//        פונקצית עזר לשחרור המזלגות
        public synchronized void release(){
            heldBy = null;
        }

        public synchronized boolean check(Philosopher philosopher){
            if (heldBy == null){
                heldBy = philosopher;
                return true;
            }
            return false;
        }


        public String toString () {
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






