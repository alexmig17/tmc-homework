package com.epam.task4.queue;

import static java.lang.Thread.currentThread;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Blocking queue implementation
 *
 * @param <E>
 * @see BlockingQueue
 */
public class BlockingQueueImpl<E> implements BlockingQueue<E> {

    private final Object poolLock = new Object();
    private final Object offerLock = new Object();
    private final Object listLock = new Object();

    private final int restrictedSize;
    private final AtomicBoolean poolAccess = new AtomicBoolean(true);
    private final AtomicBoolean offerAccess = new AtomicBoolean(true);
    private final AtomicInteger currentSize = new AtomicInteger(0);
    private final LinkedList<E> list = new LinkedList<>();

    /**
     * create blocking queue with input <code>restrictedSize</code> size
     *
     * @param restrictedSize capacity-restricted size
     */
    public BlockingQueueImpl(int restrictedSize) {
        this.restrictedSize = restrictedSize;
    }

    /**
     * @return E
     * @see BlockingQueue#poll()
     */
    @Override
    public E poll() {
        pollLock();
        E element = null;
        try {
            if (!currentThread().isInterrupted()) {
                synchronized (listLock) {
                    element = list.poll();
                }
                currentSize.decrementAndGet();
            }
        } finally {
            pollUnLock();
        }
        return element;
    }

    /**
     * @param e the element to add
     * @return
     * @see BlockingQueue#offer(Object)
     */
    @Override
    public boolean offer(E e) {
        offerLock();
        boolean added = false;
        try {
            if (!currentThread().isInterrupted()) {
                synchronized (listLock) {
                    added = list.offer(e);
                }
                currentSize.incrementAndGet();
            }
        } finally {
            offerUnLock();
        }
        return added;
    }

    /**
     * one thread can lock offer operation if current queue size less than <code>restrictedSize</code>
     * other tread will wait while offer Is Lock and/or queue size is equal to restrictedSize.
     * Or interrupted occur.
     */
    private void offerLock() {
        synchronized (offerLock) {
            while (!offerAccess.get() && !currentThread().isInterrupted()) ;
            while (currentSize.get() >= restrictedSize && !currentThread().isInterrupted()) ;
            if (!currentThread().isInterrupted()) {
                offerAccess.set(false);
            }
        }
    }

    /**
     * unlock offer operation
     */
    private void offerUnLock() {
        offerAccess.set(true);
    }

    /**
     * one thread can lock pull operation if current queue size more than 0
     * other tread will wait while pull Is Lock and/or queue size is equal to 0.
     * Or interrupted occur.
     */
    private void pollLock() {
        synchronized (poolLock) {
            while (!poolAccess.get() && !currentThread().isInterrupted()) ;
            while (currentSize.get() == 0 && !currentThread().isInterrupted()) ;
            if (!currentThread().isInterrupted()) {
                poolAccess.set(false);
            }
        }
    }

    /**
     * poll unlock
     */
    private void pollUnLock() {
        poolAccess.set(true);
    }

    /**
     * @return string represent
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return "BlockingQueueImpl{" +
                "list=" + list +
                '}';
    }
}
