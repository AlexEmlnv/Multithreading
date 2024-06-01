package org.example.nonblockingalgo;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        int ref;
        int temp;
        do {
            ref = count.get();
            temp = ref + 1;
        } while (!count.compareAndSet(ref, temp));
    }

    public int get() {
        int ref;
        int temp;
        do {
            ref = count.get();
            temp = ref;
        } while (!count.compareAndSet(ref, temp));
        return ref;
    }
}
