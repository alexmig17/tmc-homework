package com.epam.task3.queue;

import java.util.LinkedList;

/**
 * queue for Event
 * Main goal is provide event queue for single event loop.
 * If eventQueue is empty event loop will wait when call poll method
 * Original idea is only one thread can poll and multiple thread can offer
 */
public class EventQueue {

    private final LinkedList<String> list = new LinkedList<>();
    private final Object listLock = new Object();

    /**
     * poll event in head. If no event - wait
     *
     * @return event
     */
    public String poll() {
        try {
            synchronized (listLock) {
                if (list.size() == 0) {
                    listLock.wait();
                }
            }
            return list.poll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }

    /**
     * offer new event
     *
     * @param event event name
     * @return true if added
     */
    public boolean offer(String event) {
        synchronized (listLock) {
            boolean added = list.offer(event);
            listLock.notify();
            return added;
        }
    }
}
