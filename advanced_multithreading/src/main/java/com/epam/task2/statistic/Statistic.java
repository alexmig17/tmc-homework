package com.epam.task2.statistic;

import java.util.concurrent.atomic.AtomicLong;

/**
 * container for accumulate statistic about file
 * Thread safe
 */
public class Statistic {

    private AtomicLong fileCount = new AtomicLong(0);
    private AtomicLong fileSize = new AtomicLong(0);
    private AtomicLong directoryCount = new AtomicLong(0);

    /**
     * update file size
     *
     * @param fileSize file size
     */
    public void updateFileSize(long fileSize) {
        this.fileSize.updateAndGet(n -> n + fileSize);
    }

    /**
     * update file count
     *
     * @param fileCount file count
     */
    public void updateFileCount(long fileCount) {
        this.fileCount.updateAndGet(n -> n + fileCount);
    }

    /**
     * update directory count
     *
     * @param directoryCount directory count
     */
    public void updateDirectoryCount(long directoryCount) {
        this.directoryCount.updateAndGet(n -> n + directoryCount);
    }

    @Override
    public String toString() {
        return "Statistic{" +
                "fileCount=" + fileCount +
                ", fileSize=" + fileSize +
                ", directoryCount=" + directoryCount +
                '}';
    }
}
