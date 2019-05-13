package com.epam.task3.bus;

import java.util.function.Consumer;

/**
 * custom event bus
 */
public interface EventBus {

    /**
     * register new consumer for handle event
     *
     * @param event    event name
     * @param consumer consumer for handle message
     * @return boolean true if registered
     */
    boolean consumer(String event, Consumer<String> consumer);

    /**
     * send message to event
     *
     * @param event   event name
     * @param message message for event
     */
    void send(String event, String message);

    /**
     * stop event bus
     */
    void stop();
}
