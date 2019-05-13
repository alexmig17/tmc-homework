package com.epam.task4.queue;

/**
 * capacity-restricted queue with blocking <code>poll</code> operation if queue is empty
 * and blocking <code>offer</code> operation if queue is full
 *
 * @param <E>
 */
public interface BlockingQueue<E> {

    /**
     * Retrieves and removes the head of this queue,
     * or block thread while this queue is empty.
     *
     * @return the head of this queue
     */
    E poll();

    /**
     * Inserts the specified element into this queue if it is possible to do
     * so immediately without violating capacity restrictions.
     * When capacity-restricted queue is full, this method block current thread
     *
     * @param e the element to add
     * @return {@code true} if the element was added to this queue, else
     * {@code false}
     * @throws IllegalArgumentException if the specified element is null
     */
    boolean offer(E e);
}
