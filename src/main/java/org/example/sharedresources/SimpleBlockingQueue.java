package org.example.sharedresources;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int maxSize;

    public SimpleBlockingQueue(int size) {
        this.maxSize = size;
    }

    public synchronized void offer(T value) {
        if (queue.size() < maxSize) {
            queue.offer(value);
            notifyAll();
        } else {
            System.out.println("The object was not added to the queue because the maximum queue size was reached.");
        }
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        T res = queue.poll();
        notifyAll();
        return res;
    }
}