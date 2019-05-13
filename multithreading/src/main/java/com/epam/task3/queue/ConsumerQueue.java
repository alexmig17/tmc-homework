package com.epam.task3.queue;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * queue for consumer
 * Main goal is use consumer in a circle
 */
public class ConsumerQueue {

    private final Object listLock = new Object();
    private LinkedList<Consumer<String>> list = new LinkedList<>();
    private AtomicInteger pointer = new AtomicInteger(-1);

    /**
     * use consumer in a circle
     *
     * @return next consumer
     */
    public Consumer<String> next() {
        int currentIndex = nextPoint();
        Consumer<String> consumer;
        if (currentIndex != -1) {
            consumer = list.get(currentIndex);
        } else {
            consumer = null;
        }
        return consumer;
    }

    /**
     * calculate point to next Consumer
     *
     * @return point to consumer or -1 if list of consumer is empty
     */
    private int nextPoint() {
        synchronized (listLock) {
            int currentSize = list.size();
            int currentIndex;
            if (currentSize > 0) {
                currentIndex = pointer.get();
                currentIndex++;
                if (currentIndex > currentSize - 1) {
                    currentIndex = 0;
                }
            } else {
                currentIndex = -1;
            }
            pointer.set(currentIndex);
            return currentIndex;
        }
    }

    /**
     * Adds new Consumer to the tail of this list.
     *
     * @param consumer new Consumer
     * @return true if was added
     */
    public boolean offer(Consumer<String> consumer) {
        synchronized (listLock) {
            return list.offer(consumer);
        }
    }
}
