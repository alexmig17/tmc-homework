package task4;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.Arrays;

import task4.leak.A;

/**
 * Task 4. (15 points) Prepare memory leaks and find them with YourKit Profiler
 * Create simple java application (for example text file parsing or something else) with at least two memory leaks and find them using Yourkit Profiler tool.
 */
public class MemoryLeakExample {

    private static final String HTTP_URL_BIG_TXT = "http://norvig.com/big.txt";

    /**
     * run gc advanced task #4
     *
     * @param args no args
     * @throws IOException exceptions during run
     */
    public static void main(String[] args) throws IOException {
        getTimeForConnectYorKitToolsToCurrentProcess();
        staticVariableMemoryLeak();
        waitTimeBetweenTaskForMakeMoreVisibleChangesInYourKitTools();
        internBigStringMemoryLeak();
        waitTimeBetweenTaskForMakeMoreVisibleChangesInYourKitTools();
        notClosedInputStreamMemoryLeak();
    }

    /**
     * Create object from A class which contain static variable.
     * If run this method before first usage of A class - A class will load by system class loader
     * and private static variable will storage in memory in spite of it will not use anywhere.
     */
    private static void staticVariableMemoryLeak() {
        A a = new A();
        int[] variable = a.getVariable();
        System.out.println("variable length" + variable.length);
        System.out.println("variable memoryLeakItAlwaysInMemoryAndNotUsed will not use. but it steel in memory");
        System.out.println("see resources/task3/staticVariableMemoryLeak folder");
        System.out.println("After class was loaded - old generation increased and it stay keep the same level:");
        System.out.println("loaded_static_field.png");
        System.out.println("before class was loaded I click 'advance object generation number' in yourKit for start track new object in memory");
        System.out.println("after few minutes I made memory snapshot for see objects which will created but not collected by gc:");
        System.out.println("static_field_memory_leak_analiz.png");
        System.out.println("static_field_memory_leak_analiz_2.png");
        System.gc();
    }

    /**
     * Load big string form file and intern it into String pool
     *
     * @throws IOException io exceptions
     */
    private static void internBigStringMemoryLeak() throws IOException {
        File file = new File(ClassLoader.getSystemResource("task4/longText.txt").getFile());
        String fileContent = Arrays.toString(Files.readAllBytes(file.toPath())).intern();
        System.out.println("File content length" + fileContent.length());
        System.out.println("after method destroy, variable will destroy, but string content will stay in string pull");
        System.out.println("see big string loaded to metaspace: " +
                "resources/task3/internBigStringMemoryLeak/itern_big_string.png");
        System.out.println("metaspace is not part of heap, but in any case memory is limited");
        System.gc();
    }

    /**
     * create url connection. Open input stream and stay it opened.
     * do it in eternal cycle
     *
     * @throws IOException io exceptions
     */
    private static void notClosedInputStreamMemoryLeak() throws IOException {
        System.out.println("see resources/task3/notClosedInputStreamMemoryLeak folder");
        System.out.println("there is example how memory looks like if input stream is closed:");
        System.out.println("socket_report_when_input_stream_closed_properly.png");
        System.out.println("next example show how memory looks like if stream is not closed and it has strong reference");
        System.out.println("not_closed_input_stream_added_to_list_with_strong_reference.png");
        System.out.println("next example show how memory looks like if stream is not closed and no strong reference.");
        System.out.println("you can see that gc collect almost all objects in eden:");
        System.out.println("gc_connection_close_when_gc_collect_connection.png");
        System.out.println("there is how socket report look like at the same time when gc collect objects:");
        System.out.println("socket_report_connection_close_when_gc_collect_connection.png");
        System.out.println("it mean that leak happened only in eden space");
        System.out.println("there is break point for show that during finalize close method was revoked:");
        System.out.println("finalizer_close_connection.png");

        while (shouldContinue()) {
            URLConnection conn
                    = new URL(HTTP_URL_BIG_TXT).openConnection();
            conn.setConnectTimeout(2000000000);
            conn.setReadTimeout(2000000000);
            InputStream inputStream = conn.getInputStream();
            System.out.println(inputStream.read());
            System.out.println(inputStream);
            sleep(10);
            if (Thread.interrupted()) {
                break;
            }
        }
    }

    /**
     * Wait time between task for make more visible changes in your kit tools
     */
    private static void waitTimeBetweenTaskForMakeMoreVisibleChangesInYourKitTools() {
        sleep(60000);
    }

    /**
     * Get time for manually connect YorKit tools to current process
     * Also it is allow make memory snapshot without objects initiated during startup this application
     */
    private static void getTimeForConnectYorKitToolsToCurrentProcess() {
        sleep(20000);
    }

    /**
     * Should continue do 'while' cycle
     *
     * @return boolean
     */
    private static boolean shouldContinue() {
        return true;
    }

    /**
     * Sleep current thread in millisecond
     *
     * @param timeInMillisecond millisecond
     */
    private static void sleep(long timeInMillisecond) {
        try {
            Thread.sleep(timeInMillisecond);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
