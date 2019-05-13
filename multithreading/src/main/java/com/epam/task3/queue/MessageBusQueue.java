package com.epam.task3.queue;

import java.util.LinkedList;

/**
 * queue for message bus
 * one instance of this queue is design for storage messages for single event
 */
public class MessageBusQueue {

    private final LinkedList<String> list = new LinkedList<>();
    private final Object listLock = new Object();

    /**
     * poll message from head
     *
     * @return message
     */
    public String poll() {
        synchronized (listLock) {
            return list.poll();
        }
    }

    /**
     * offer new message
     *
     * @param message message
     * @return true if added
     */
    public boolean offer(String message) {
        synchronized (listLock) {
            return list.offer(message);
        }
    }
}
