package com.epam.task2;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.io.File;
import java.util.Scanner;

import com.epam.task2.scanner.FileScannerTask;
import com.epam.task2.statistic.Statistic;

/**
 * Task 3. (25 points) File Scanner via FJK
 * Create CLI application that scans a specified folder and provides detailed statistics:
 * 1. File count
 * 2. Folder count
 * 3. Size (sum of all files size) (Similar like windows context menu "properties")
 * Since the folder may contain huge number of files the scanning process should be executed in a separate thread displaying an informational message with some simple animation like progress bar in CLI (up to you, but I'd like to see that task is in progress).
 * Once task is done, the statistics should be displayed in the output immediately.
 * Additionally, there should be ability to interrupt the process pressing some reserved key (for instance "c").
 * Of course, use Fork-Join Framework for implementation parallel scanning.
 */
public class FileScannerApp {

    private static final String PATH = "C:\\Users\\Alex\\Desktop\\Зоопарк";
    private static File file;

    /**
     * main for task 3
     *
     * @param args no args
     */
    public static void main(String[] args) {

        file = askFilePath();
        Thread thread = new Thread(FileScannerApp::showStatistic);
        thread.start();
        System.out.println("put any int for exit");
        Scanner scan = new Scanner(System.in);
        scan.nextLine();
        thread.interrupt();
    }

    /**
     * show statistic for current file variable
     */
    private static void showStatistic() {
        FileScannerTask fileScannerTask = new FileScannerTask(file);
        Statistic statistic = fileScannerTask.compute();
        if (!Thread.currentThread().isInterrupted()) {
            System.out.println();
            System.out.println(statistic);
        } else {
            System.out.println();
            System.out.println("statistic before interrupted");
            System.out.println(statistic);
        }
    }

    /**
     * ask file path
     *
     * @return valid File by imputed path or default if empty enter
     */
    private static File askFilePath() {

        Scanner scan = new Scanner(System.in);
        boolean notValid = true;
        File file = new File(PATH);
        while (notValid) {
            System.out.println("put any file path");
            String path = scan.nextLine();
            if (isBlank(path)) {
                notValid = false;
            } else {
                file = new File(path);
                if (file.exists()) {
                    notValid = false;
                }
            }
        }
        return file;
    }
}
