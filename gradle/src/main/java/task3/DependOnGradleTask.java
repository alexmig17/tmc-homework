package task3;

/**
 * task3:
 * <p>
 * 1. Make the ‘MyDate’ task depend on the ‘startGradle’ task.
 * 2. Execute the ‘MyDate’ task.
 * 3. Execute gradle tasks --all.
 * 4. The --dry-run (or -m) command line option executes the build but disables all actions.
 * Execute ‘MyDate’ with the dry-run option.
 * 5. Add some top level println statements to the script.
 * 6. Add a println statement to the configuration block of the ‘MyDate’ task.
 * 7. Execute the ‘startGradle’ task and analyze the output.
 */
public class DependOnGradleTask {

    /**
     * run task3
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
        System.out.println("See screenshots from resources/task1 folder");
        System.out.println("1. Make the ‘MyDate’ task depend on the ‘startGradle’ task.");
        System.out.println("2. Execute the ‘MyDate’ task.");
        System.out.println("See Mydate_depend_on_start_gradle.png");

        System.out.println("3. Execute gradle tasks --all.");
        System.out.println("See gradle_all.png");

        System.out.println("4. The --dry-run (or -m) command line option executes the build but disables all actions.\n" +
                " * Execute ‘MyDate’ with the dry-run option.");
        System.out.println("See mydate_dry_run.png");

        System.out.println("5. Add some top level println statements to the script.");
        System.out.println("6. Add a println statement to the configuration block of the ‘MyDate’ task.");
        System.out.println("See my_date_before_after.png");

        System.out.println("7. Execute the ‘startGradle’ task and analyze the output.");
        System.out.println("See start_gradle_before_after.png");
    }
}
