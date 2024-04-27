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
       try {
           first.join();
           second.join();
       } catch (InterruptedException e) {
           throw new RuntimeException(e);
       }
       System.out.println("The work is completed");
    }
}
