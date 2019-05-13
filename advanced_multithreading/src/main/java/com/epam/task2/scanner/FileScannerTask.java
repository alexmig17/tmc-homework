package com.epam.task2.scanner;

import static java.lang.Thread.currentThread;
import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;

import com.epam.task2.statistic.Statistic;

/**
 * task for scan folder recursive scan subfolder and provide statistic info accumulated in Statistic object
 */
public class FileScannerTask extends RecursiveTask<Statistic> {

    private static final String PROGRESS_BAR_TEMPLATE = "\r %d/%d %d%%";
    private final File file;
    private final Statistic statistic;
    private final AtomicInteger totalTasks;
    private final AtomicInteger totalDone;

    private FileScannerTask(File file, Statistic statistic, AtomicInteger totalTasks, AtomicInteger totalDone) {
        this.file = file;
        this.statistic = statistic;
        this.totalTasks = totalTasks;
        this.totalDone = totalDone;
    }

    public FileScannerTask(File file) {
        this.file = file;
        this.statistic = new Statistic();
        this.totalTasks = new AtomicInteger(0);
        this.totalDone = new AtomicInteger(0);
    }

    /**
     * compute statistic info for current directory/file
     * and run recurcive task for inner directories/files
     *
     * @return Statistic
     */
    @Override
    public Statistic compute() {

        if (file.isDirectory()) {
            ofNullable(file.listFiles()).ifPresent(files -> {
                List<ForkJoinTask> forkTasks = createForkJoinTasksAndReloadProgressBar(files);
                updateFileCount(files);
                updateDirectoryCount(files);
                joinTasksAndReloadProgressBar(forkTasks);
            });
        } else if (file.isFile()) {
            statistic.updateFileSize(file.length());
        }
        return statistic;
    }

    /**
     * join each ForkJoinTask and update progress bar
     *
     * @param forkTasks List of ForkJoinTask
     */
    private void joinTasksAndReloadProgressBar(List<ForkJoinTask> forkTasks) {
        Iterator<ForkJoinTask> iterator = forkTasks.iterator();
        while (iterator.hasNext()) {
            if (currentThread().isInterrupted()) {
                break;
            } else {
                ForkJoinTask task = iterator.next();
                task.join();
                if (task.isDone()) {
                    totalDone.incrementAndGet();
                    reloadStatusBar();
                    iterator.remove();
                }
            }
        }
    }

    /**
     * create fork join tasks and add reload task bar
     *
     * @param files files
     * @return list of ForkJoinTask
     */
    private List<ForkJoinTask> createForkJoinTasksAndReloadProgressBar(File[] files) {
        List<ForkJoinTask> forkTasks = stream(files).map(this::newTask)
                .peek(FileScannerTask::fork)
                .collect(toList());
        if (forkTasks.size() > 0) {
            totalTasks.updateAndGet(n -> n + forkTasks.size());
            reloadStatusBar();
        }
        return forkTasks;
    }

    /**
     * calculate directory count and update statistic
     *
     * @param files array of files
     */
    private void updateDirectoryCount(File[] files) {
        long directoryCount = stream(files).filter(File::isDirectory).count();
        if (directoryCount > 0) {
            statistic.updateDirectoryCount(directoryCount);
        }
    }

    /**
     * calculate file count and update statistic
     *
     * @param files array of files
     */
    private void updateFileCount(File[] files) {
        long filesCount = stream(files).filter(File::isFile).count();
        if (filesCount > 0) {
            statistic.updateFileCount(filesCount);
        }
    }

    /**
     * create new FileScannerTask
     *
     * @param file file for scan
     * @return FileScannerTask
     */
    private FileScannerTask newTask(File file) {
        return new FileScannerTask(file, statistic, totalTasks, totalDone);
    }

    /**
     * reload status bar info
     */
    private void reloadStatusBar() {
        int total = totalTasks.get();
        int done = totalDone.get();
        String message = String.format(PROGRESS_BAR_TEMPLATE, total, done, (100 * done / total));
        synchronized (PROGRESS_BAR_TEMPLATE) {
            System.out.print(message);
        }
    }
}
