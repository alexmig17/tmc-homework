package com.epam.task3.bus;

import static java.util.Optional.ofNullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import com.epam.task3.queue.ConsumerQueue;
import com.epam.task3.queue.EventQueue;
import com.epam.task3.queue.MessageBusQueue;

/**
 * Event bus implementation
 */
public class EventBusImpl implements EventBus {

    private final Object queueMapLock = new Object();
    private final Object consumerMapLock = new Object();
    private final Map<String, MessageBusQueue> queueMap = new ConcurrentHashMap<>();
    private final Map<String, ConsumerQueue> consumerMap = new ConcurrentHashMap<>();
    private final ExecutorService workers;
    private final ExecutorService eventLoop;
    private final EventQueue events = new EventQueue();

    /**
     * new Event Bus
     *
     * @param workerSize count of workers
     */
    public EventBusImpl(int workerSize) {
        this.workers = Executors.newFixedThreadPool(workerSize);
        this.eventLoop = Executors.newSingleThreadExecutor();
        eventLoop.submit(this::eventLoopTask);
    }

    /**
     * thread save
     *
     * @param event    event name
     * @param consumer consumer for handle message
     * @see EventBus#consumer(String, Consumer)
     */
    @Override
    public boolean consumer(String event, Consumer<String> consumer) {
        createQueueForEventIfNotExist(event);
        return registerConsumer(event, consumer);
    }

    /**
     * thread save
     * send message by event
     * if no consumer - no actions
     *
     * @param event   event name
     * @param message message for event
     * @see EventBus#send(String, String)
     */
    @Override
    public void send(String event, String message) {
        ofNullable(queueMap.get(event))
                .filter(queue -> queue.offer(message))
                .ifPresent(messageBusQueue -> events.offer(event));
    }

    /**
     * @see EventBus#stop()
     */
    @Override
    public void stop() {
        workers.shutdown();
        eventLoop.shutdownNow();
        try {
            workers.awaitTermination(10000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            workers.shutdownNow();
        }
    }

    /**
     * register new consumer for handle input event message.
     *
     * @param event    event name
     * @param consumer new consumer
     * @return true if registered
     */
    private boolean registerConsumer(String event, Consumer<String> consumer) {
        return getConsumerQueueForEventCreateIfNotExist(event).offer(consumer);
    }

    /**
     * get consumer queue for event create if not exist
     *
     * @param event event name
     * @return ConsumerQueue
     */
    private ConsumerQueue getConsumerQueueForEventCreateIfNotExist(String event) {
        ConsumerQueue queue = consumerMap.get(event);
        if (queue == null) {
            synchronized (consumerMapLock) {
                queue = consumerMap.get(event);
                if (queue == null) {
                    queue = new ConsumerQueue();
                    consumerMap.put(event, queue);
                }
            }
        }
        return queue;
    }

    /**
     * create queue for event if not exist
     *
     * @param event event name
     */
    private void createQueueForEventIfNotExist(String event) {
        MessageBusQueue messageBusQueue = queueMap.get(event);
        if (messageBusQueue == null) {
            synchronized (queueMapLock) {
                if (queueMap.get(event) == null) {
                    messageBusQueue = new MessageBusQueue();
                    queueMap.put(event, messageBusQueue);
                }
            }
        }
    }

    /**
     * task for event loop:
     * get available event, find consumer for him and run founded task in worker pool
     * if no events - wait
     */
    private void eventLoopTask() {
        while (!Thread.currentThread().isInterrupted()) {
            String event = events.poll();
            ofNullable(event).map(consumerMap::get)
                    .map(ConsumerQueue::next)
                    .map(stringConsumer -> (Runnable) () -> stringConsumer.accept(queueMap.get(event).poll()))
                    .ifPresent(workers::submit);
        }
    }
}
