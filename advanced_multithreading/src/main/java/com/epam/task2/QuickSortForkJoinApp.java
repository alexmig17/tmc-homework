package com.epam.task2;

import java.util.Random;

/**
 * Task 2. (15 points) Multithreading Sorting via FJK
 * Implement Merge Sort or Quick Sort algorithm that sorts huge array of integers in parallel using Fork/Join framework.
 */
public class QuickSortForkJoinApp {

    private static final int ARRAY_SIZE = 1000;
    private static final int MIN = -100;
    private static final int MAX = 900;

    public static void main(String[] args) {

        int[] array = generateArray();
        sortArrayByAscending(array);
        printArray(array);
    }

    /**
     * print array to the console
     *
     * @param array input array
     */
    private static void printArray(int[] array) {
        for (int anArray : array) {
            System.out.println(anArray);
        }
    }

    /**
     * sort array by ascending
     *
     * @param array array to sort
     */
    private static void sortArrayByAscending(int[] array) {
        QuickSortForkJoin quickSortForkJoin = new QuickSortForkJoin(array);
        quickSortForkJoin.compute();
    }

    /**
     * generate int array
     *
     * @return int array
     */
    private static int[] generateArray() {
        Random random = new Random();
        int[] array = new int[ARRAY_SIZE];
        for (int i = 0; i < ARRAY_SIZE; i++) {
            array[i] = random.nextInt(MAX - MIN + 1) + MIN;
        }
        return array;
    }
}
