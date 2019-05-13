package task2;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * class with implemented task2:
 * <p>
 * Take the code and run
 * public class Main {
 *     public static void main(String[] args) {
 *         List&lt;Object&gt; list = new ArrayList&lt;Object&gt;();
 *         while(true) {
 *              list.add(new Object());
 *         }
 *     }
 * }
 * <p>
 * Connect to the running JVM with VisualVM with installed GC plugin. Observe GC curve and
 * memory regions.
 * 1. Apply changes to the following app code to make GC curve have peaks that are
 * more frequent.
 * 2. Tune GC settings via JVM flags (change GC, maybe) to make GC curve have peaks
 * that are more frequent.
 * 3. Tune Heap regions via JVM flags to make GC curve have peaks that are more
 * frequent.
 * Make screenshots for each subtask and prove your power of JVM – master!
 **/
public class VisualVmAndGCPlugin {

    /**
     * main method for run task1
     *
     * @param args no custom args
     */
    public static void main(String[] args) {
        printMenu();
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        switch (choice) {
            case 1:
                litterTheHeap();
                break;
            case 2:
                changeCode();
                break;
            case 3:
                g1();
                break;
            case 4:
                g1ReduceRegion();
                break;
            case 5:
                newRatio7();
                break;
            default:
                errorMessageOnOtherChoice(choice);
                break;
        }
        changeCode();
    }

    /**
     * menu
     */
    private static void printMenu() {
        System.out.println("Please choose task jvm:");
        System.out.println("1: default method for litter heap. See result in \\src\\main\\resources\\task2\\default.png");
        System.out.println("2: code changes for make peaks more frequent. See result in \\src\\main\\resources\\task2\\codeChanges.png");
        System.out.println("3: change GC to G1 for make peaks more frequent. See result in \\src\\main\\resources\\task2\\g1.png");
        System.out.println("4: change GC to G1 and reduce region to 1m. No difference between 3 choice. See result in \\src\\main\\resources\\task2\\g1ReduceRegion.png");
        System.out.println("5: change ratio to 7 for make peaks more frequent. See result in \\src\\main\\resources\\task2\\newRatio7.png");
    }

    /**
     * Error message on other choice
     *
     * @param choice user choice
     */
    private static void errorMessageOnOtherChoice(int choice) {
        System.out.println(String.format("%d command doesn't exist. You can choose only available values. Please try again.", choice));
    }

    /**
     * method for litter the heap
     * see default.png
     */
    private static void litterTheHeap() {
        List<Object> list = new ArrayList<>();
        Long startTime = getNow();
        while (true) {
            list.add(new Object());
            if (getNow() - startTime > 40000) {
                break;
            }
        }
    }

    /**
     * change gc by
     * -XX:+UseG1GC
     * see g1 default.png
     * peaks become more frequent.
     */
    private static void g1() {
        litterTheHeap();
    }

    /**
     * change gc by
     * -XX:+UseG1GC
     * reduce region by
     * -XX:G1HeapRegionSize=1m
     * see g1 reduce region.png
     * peaks the same.
     */
    private static void g1ReduceRegion() {
        litterTheHeap();
    }

    /**
     * default gc
     * change ratio:
     * -XX:NewRatio=7
     * see
     * new ration 7.png
     * peaks become more frequent.
     */
    private static void newRatio7() {
        litterTheHeap();
    }

    private static void changeCode() {
        Long startTime = getNow();
        while (true) {
            List<Byte[]> list = new ArrayList();
            for (int i = 0; i < 10000; i++) {
                Byte[] bytes = new Byte[5000];
                list.add(bytes);
            }
            if (getNow() - startTime > 40000) {
                break;
            }
        }
    }

    private static long getNow() {
        return Instant.now().toEpochMilli();
    }
}
