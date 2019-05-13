package main.java.task5;

/**
 * Task 5. (10 points) Dependencies
 * <p>
 * 1.	Add the Maven Central repository and a configuration named myConfig.
 * 2.	Assign the org.apache.httpcomponents:httpclient:4.5.6 dependency to myConfig.
 * 3.	Add a task showDeps that prints out the files of the myConfig configuration.
 * 4.	Add task copyDeps that copies the files of the myConfig configuration into the build/deps dir.
 * 5.	Execute gradle dependencies.
 */
public class GradleConfigurationTask {

    /**
     * execute task 5
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
        System.out.println("See screenshots from resources/task5 folder");

        System.out.println("Add a task showDeps that prints out the files of the myConfig configuration.");
        System.out.println("!!!!!! see gradle_show_deps_task.png");

        System.out.println("Add task copyDeps that copies the files of the myConfig configuration into the build/deps dir.");
        System.out.println("!!!!!! see gradle_copy_deps_task.png");

        System.out.println("Execute gradle dependencies.");
        System.out.println("!!!!!! see gradle_dependencies_task.png");
    }
}
