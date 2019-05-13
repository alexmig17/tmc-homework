package task1;

/**
 * class with implemented task1:
 * Create several instances of Runnable interface with different behavior using lambda
 * expressions. Run these lambdas via Thread API.
 */
public class RunnableUsingLambda {

    /**
     * main method for run task1
     * @param args no custom args
     */
    public static void main(String[] args) {

        runInNewThread(() -> System.out.println("Hello world"));
        runInNewThread(() -> System.out.println("Second thread"));
        runInNewThread(() -> System.out.println("Third thread"));
        runInNewThread(RunnableUsingLambda::doSomething);
    }

    /**
     * create new thread with input runnable and run
     * @param runnable Runnable object with behavior for new thread
     */
    private static void runInNewThread(Runnable runnable) {
        new Thread(runnable).run();
    }

    /**
     * print 'do something'
     */
    private static void doSomething() {
        System.out.println("do something");
    }
}
