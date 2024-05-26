package org.example.sharedresources;

import org.example.waitnotify.SimpleBlockingQueue;
import org.junit.jupiter.api.Test;

class SimpleBlockingQueueTest {

    @Test
    public void whenWork() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread consumer = new Thread(
                () -> {
                    try {
                        var res = queue.poll();
                        System.out.println(Thread.currentThread().getName() + " got from queue value '" + res + "'.");
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                , "Consumer");

        Thread producer = new Thread(
                () -> {
                    try {
                        queue.offer(1);
                        System.out.println(Thread.currentThread().getName() + " saved in queue value '" + 1 + "'.");
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                , "Producer");

        producer.start();
        producer.join();
        consumer.start();
        consumer.join();
    }
}