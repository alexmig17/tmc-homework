package task4;

/**
 * task4:
 * 1. Run the tests with different settings for forkEvery. What do you see?
 * 2. Run the tests with different settings for maxParallelForks. What do you see?
 * 3. Experiment with some of the testLogging options. (It may help to make some tests
 * fail first.)
 * 4. (Optional) Add a listener that, if a test fails, opens the test results XML file.
 */
public class GradleUnitTestExample {

    /**
     * task4
     *
     * @param args no args
     */
    public static void main(String[] args) {
        printMenu();
    }

    /**
     * print menu
     */
    private static void printMenu() {
        System.out.println("See screenshots from resources/task4 folder");

        System.out.println("1. Run the tests with different settings for forkEvery. What do you see?");
        System.out.println("See screenshots gradle_fork_1.png and gradle_fork_2.png");

        System.out.println("Experiment with some of the testLogging options. (It may help to make some tests fail first.)");
        System.out.println("See screenshots: " +
                "gradle_full_exception.png full exception format" +
                "gradle_short_exception.png short exception format" +
                "gradle_run_tests.png just run test" +
                "gradle_no_stack_trace_cause_and_exceptions.png without exceptions");

        System.out.println("4. (Optional) Add a listener that, if a test fails, opens the test results XML file.");
        System.out.println("Not implement");
    }

    /**
     * return 'test1' String
     *
     * @return String
     */
    public static String utilMethod() {
        return "test1";
    }

    /**
     * return 'test2' String
     *
     * @return String
     */
    public static String utilMethod2() {
        return "test2";
    }

    /**
     * return 'test3' String
     *
     * @return String
     */
    public static String utilMethod3() {
        getNull().intern();
        return "test3";
    }

    /**
     * return null
     *
     * @return null
     */
    private static String getNull() {
        return null;
    }
}
