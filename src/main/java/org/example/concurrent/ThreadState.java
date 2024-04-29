package org.example.concurrent;

public class ThreadState {
    public static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " works");
        }
    }

   public static void main(String[] args) {
       Thread first = new MyThread();
       Thread second = new MyThread();
       first.start();
       second.start();
       while (first.getState() != Thread.State.TERMINATED
              || second.getState() != Thread.State.TERMINATED) {
           System.out.println(first.getName() + ": " + first.getState());
           System.out.println(second.getName() + ": " + second.getState());
       }
       System.out.println("The work is completed!");
    }
}
