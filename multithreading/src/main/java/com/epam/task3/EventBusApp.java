package com.epam.task3;

import static java.lang.String.format;
import static java.lang.Thread.currentThread;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.task3.bus.EventBusImpl;

/**
 * Event Bus Application
 * Application for show how custom event bus working
 * Task 3. (20 points) «Where’s your bus, dude? »
 * Implement message bus using Producer-Consumer pattern.
 * 1. Implement asynchronous message bus. Do not use queue implementations from
 * java.util.concurrent.
 * 2. Implement producer, which will generate and post randomly messages to the
 * queue
 * 3. Implement consumer, which will consume messages on specific topic and log to
 * the console message payload
 * Optional: 4. Application should create several consumers and producers that run in parallel.
 */
public class EventBusApp {

    private static final Logger logger = LogManager.getLogger(EventBusApp.class);
    private static final String EVENT_1 = "excellent news";
    private static final String EVENT_2 = "god news";
    private static final String SEND_PATTERN = "send %d";
    private static final String MESSAGE_PATTERN = "number %d";
    private static final String EVENT_MESSAGE_PATTERN = "%s:event:%s:message:%s:consumer:%s";

    /**
     * main method for run task 3 event bus
     *
     * @param args no args
     */
    public static void main(String[] args) throws InterruptedException {

        EventBusImpl eventBus = new EventBusImpl(4);
        for (int i = 0; i < 5; i++) {
            String event = i % 2 == 0 ? EVENT_1 : EVENT_2;
            String consumerName = String.valueOf(i);
            eventBus.consumer(event, s -> logger.debug(getEventMessage(s, event, consumerName)));
        }

        for (int i = 0; i < 50; i++) {
            eventBus.send(i % 2 == 0 ? EVENT_1 : EVENT_2, format(MESSAGE_PATTERN, i));
            logger.debug(format(SEND_PATTERN, i));
        }

        Thread.sleep(5000);
        eventBus.stop();
    }

    /**
     * get event message
     *
     * @param message      message send by event bus
     * @param event        event name
     * @param consumerName consumer name
     * @return event message
     */
    private static String getEventMessage(String message, String event, String consumerName) {
        return String.format(EVENT_MESSAGE_PATTERN, currentThread().getName(), event, message, consumerName);
    }
}
