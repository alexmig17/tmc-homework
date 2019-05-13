package com.epam.task2;

import java.util.Optional;
import java.util.concurrent.RecursiveTask;

/**
 * quick sort using java fork join
 */
public class QuickSortForkJoin extends RecursiveTask<Optional> {

    private final int[] arr;
    private final int startIndex;
    private final int endIndex;

    /**
     * @param arr array for sort
     */
    QuickSortForkJoin(int[] arr) {
        this.arr = arr;
        startIndex = 0;
        endIndex = arr.length - 1;
    }

    /**
     * @param arr        array for sort
     * @param startIndex start index for sorting
     * @param endIndex   end index for sorting
     */
    private QuickSortForkJoin(int[] arr, int startIndex, int endIndex) {
        this.arr = arr;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    /**
     * sort array in range
     *
     * @return null
     */
    @Override
    protected Optional compute() {
        if (startIndex < endIndex + 1) {
            int pivotIndex = partition();
            QuickSortForkJoin quickSortForkJoinLeft = new QuickSortForkJoin(arr, startIndex, pivotIndex - 1);
            QuickSortForkJoin quickSortForkJoinRight = new QuickSortForkJoin(arr, pivotIndex + 1, endIndex);
            quickSortForkJoinLeft.fork();
            quickSortForkJoinRight.compute();
            quickSortForkJoinLeft.join();
        }
        return Optional.empty();
    }

    /**
     * partition array to left and right part.
     * left right will contain int less than pivot value, right will contain int greater then pivot
     *
     * @return pivot index
     */
    private int partition() {
        int border = startIndex + 1;
        for (int i = border; i <= endIndex; i++) {
            if (arr[i] < arr[startIndex]) {
                swap(i, border++);
            }
        }
        swap(startIndex, border - 1);
        return border - 1;
    }

    /**
     * swap values in index 1 and index2
     *
     * @param index1 index
     * @param index2 index
     */
    private void swap(int index1, int index2) {
        int buff = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = buff;
    }
}
