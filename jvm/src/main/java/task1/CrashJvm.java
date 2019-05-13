package task1;

import static java.lang.Thread.sleep;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;

import task1.beans.LinkedString;
import task1.classloader.MetaspaceClassLoader;
import task1.classloader.StackOverClassLoader;

/**
 * class with implemented task1:
 * Design and implement code that will introduce:
 * 1. java.lang.OutOfMemoryError: Java heap space. You can use different data
 * structures. Do not tune heap size.
 * 2. java.lang.OutOfMemoryError: Java heap space. Create big objects continuously and
 * make them stay in memory. Do not use arrays or collections.
 * 3. java.lang.OutOfMemoryError: Metaspace. Load classes continuously and make them
 * stay in memory.
 * 4. java.lang.StackOverflowError. Use eternalCycle methods. Don’t tune stack size.
 * 5. java.lang.StackOverflowError. Do not use eternalCycle methods. Don’t tune stack size.
 */
public class CrashJvm {

    private final static MetaspaceClassLoader METASPACE_CLASS_LOADER = new MetaspaceClassLoader();
    private final static StackOverClassLoader STACK_OVER_CLASS_LOADER = new StackOverClassLoader();
    private static final String CLASS_NAME = "Name";

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        printMenu();
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        switch (choice) {
            case 1:
                createOneBigObject();
                break;
            case 2:
                createObjectsContinuouslyAndStayItInMemory();
                break;
            case 3:
                generateAndLoadClasses();
                break;
            case 4:
                eternalCycle(0);
                break;
            case 5:
                stackOverFlowManyFieldsInStack();
                break;
            default:
                errorMessageOnOtherChoice(choice);
                break;
        }
    }

    private static void errorMessageOnOtherChoice(int choice) {
        System.out.println(String.format("%d command doesn't exist. You can choose only available values. Please try again.", choice));
    }

    private static void printMenu() {
        System.out.println("Please choose how you would like crash jvm:");
        System.out.println("1: java.lang.OutOfMemoryError: Java heap space. \nYou can use different data" +
                "structures. Do not tune heap size.");
        System.out.println("2: java.lang.OutOfMemoryError: Java heap space. \nCreate big objects continuously and" +
                " * make them stay in memory. Do not use arrays or collections");
        System.out.println("3: java.lang.OutOfMemoryError: Metaspace. \nLoad classes continuously and make them" +
                "stay in memory. \nPlease limit metaspace for java 8. For example -XX:MaxMetaspaceSize=20m");
        System.out.println("4: java.lang.StackOverflowError. \nUse eternalCycle methods. Don’t tune stack size.");
        System.out.println("5: java.lang.StackOverflowError. \nDo not use eternalCycle methods. Don’t tune stack size.");
    }

    private static void stackOverFlowManyFieldsInStack() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        Supplier supplier = (Supplier) STACK_OVER_CLASS_LOADER.loadClass(CLASS_NAME).newInstance();
        supplier.get();
    }

    private static void createObjectsContinuouslyAndStayItInMemory() throws IOException, InterruptedException {
        LinkedString peak = new LinkedString(newBigString());
        for (int i = 0; i < 400; i++) {
            LinkedString newPeak = new LinkedString(newBigString());
            newPeak.setLinkedString(peak);
            peak = newPeak;
            sleep(100);
        }
    }

    private static void createOneBigObject() {
        System.out.println(Arrays.toString(new byte[1000000 * 2048]));
    }

    private static void eternalCycle(int i) {
        System.out.println("loop number: " + i);
        int floodVariableForReduceLoopCount = 0;
        int floodVariableForReduceLoopCount1 = 1;
        int floodVariableForReduceLoopCount2 = 2;
        int floodVariableForReduceLoopCount3 = 3;
        int floodVariableForReduceLoopCount4 = 4;
        int floodVariableForReduceLoopCount5 = 5;
        int floodVariableForReduceLoopCount6 = 6;
        int floodVariableForReduceLoopCount7 = 7;
        int floodVariableForReduceLoopCount8 = 8;
        int floodVariableForReduceLoopCount9 = 9;
        eternalCycle(++i);
    }

    private static String newBigString() throws IOException {
        File file = new File(ClassLoader.getSystemResource("task1/Duma.txt").getFile());
        return new String(Files.readAllBytes(file.toPath()));
    }

    private static void generateAndLoadClasses() throws ClassNotFoundException {
        List<Class> classes = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            classes.add(METASPACE_CLASS_LOADER.loadClass("Test" + i));
        }
    }
}
