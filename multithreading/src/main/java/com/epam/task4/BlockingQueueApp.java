package com.epam.task4;

import static java.lang.Thread.currentThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.task4.queue.BlockingQueue;
import com.epam.task4.queue.BlockingQueueImpl;

/**
 * Main class for blocking queue task
 * Task 4. (20 points)
 * Create simple object pool with support for multithreaded environment. No any extra
 * inheritance, polymorphism or generics needed here, just implementation of simple class:
 */
public class BlockingQueueApp {

    private static final BlockingQueue<String> BLOCKING_QUEUE = new BlockingQueueImpl<>(20);
    private static final String NEW_LINE = "\n";
    private static final String COLON = ":";
    private static final Logger logger = LogManager.getLogger(BlockingQueueApp.class);
    private static AtomicInteger writeNumber = new AtomicInteger(0);
    private static AtomicInteger readNumber = new AtomicInteger(0);

    /**
     * main method for run task 4
     *
     * @param args no args
     */
    public static void main(String[] args) throws InterruptedException {
        Thread thread2 = executeTask(22, BlockingQueueApp::writeTask, 10500);
        Thread thread = executeTask(50, BlockingQueueApp::readTask, 10000);
        thread.join();
        thread2.join();
        logger.debug("other elements: " + BLOCKING_QUEUE);
    }

    /**
     * execute inputted 'runnable' with limited of time for running. Method was design for 'runnable' with eternal execution.
     * It mean that 'runnable' should has eternal cycle and do not stop until interrupt occur
     *
     * @param taskCount        count of tasks will be created based on inputted 'runnable'.  pull threads size will the same like 'taskCount'
     *                         because 'runnable' with eternal execution have never stopped until interrupted.
     *                         And optimal ratio is one task - one thread.
     * @param runnable         task for execute.
     * @param shutdownSchedule timer in millisecond for shutdown all thread. Timer start counting after submitting all tasks
     * @return Thread where was generated tasks and executed in thread pool
     */
    private static Thread executeTask(int taskCount, Runnable runnable, int shutdownSchedule) {
        Thread thread = new Thread(() -> {
            ExecutorService executorService = Executors.newFixedThreadPool(taskCount);
            for (int i = 0; i < taskCount; i++) {
                executorService.submit(runnable);
            }
            sleep(shutdownSchedule);
            executorService.shutdownNow();
            sleep(2000);
            executorService.shutdownNow().stream().peek(executed -> logger.debug("can not shutdown task " + executed));
        });
        thread.start();
        return thread;
    }

    /**
     * sleep current thread in millisecond
     *
     * @param millisecond millisecond for sleep
     */
    private static void sleep(int millisecond) {
        try {
            Thread.sleep(millisecond);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * task for read message from blocking queue
     */
    private static void readTask() {
        while (!currentThread().isInterrupted()) {
            String message = "read:" + currentThread().getName() + COLON + readNumber.incrementAndGet() + NEW_LINE + BLOCKING_QUEUE.poll();
            if (!currentThread().isInterrupted()) {
                logger.debug(message);
            } else {
                logger.debug("after:interrupt:" + message);
            }
        }
    }

    /**
     * task for write message to blocking queue
     */
    private static void writeTask() {
        while (!currentThread().isInterrupted()) {
            String message = "write" + COLON + currentThread().getName() + COLON + writeNumber.incrementAndGet();
            BLOCKING_QUEUE.offer(message);
            if (!currentThread().isInterrupted()) {
                logger.debug(message);
            } else {
                logger.debug("after:interrupt:" + message);
            }
        }
    }
}
