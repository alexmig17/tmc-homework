package task1;

/**
 * task1 (5 points):
 * 1. Download gradle from http://gradle.org/downloads and extract it.
 * 2. Add an environment variable GRADLE_HOME pointing to the top-level directory of
 * the Gradle distribution.
 * 3. Add $GRADLE_HOME/bin to the PATH environment variable. (Windows:
 * %GRADLE_HOME%\bin)
 * 4. Activate the Gradle Daemon by setting the environment variable GRADLE_OPTS to
 * -Dorg.gradle.daemon=true.
 * 5. Open a terminal and execute gradle -v.
 * 6. Start the UI with gradle –gui
 * 7. Add a 'startGradle' task that prints &#39;The Gradle is building&#39;.
 * 8. Execute this task
 * 9. Add a 'MyDate' task that prints out the current date.
 * 10. Execute this task
 */
public class GradleConsoleTask {

    public static void main(String[] args) {
        printMenu();
    }

    /**
     * print menu
     */
    private static void printMenu() {
        System.out.println("See screenshots from resources/task1 folder");
        System.out.println("1. Download gradle from http://gradle.org/downloads and extract it.");
        System.out.println("2. Add an environment variable GRADLE_HOME pointing to the top-level directory of" +
                " the Gradle distribution.");
        System.out.println("3. Add $GRADLE_HOME/bin to the PATH environment variable. (Windows:" +
                " %GRADLE_HOME%\\bin)");

        System.out.println("4. Activate the Gradle Daemon by setting the environment variable GRADLE_OPTS to" +
                " -Dorg.gradle.daemon=true.");
        System.out.println("!!!!!! see gradle_env_options.png");

        System.out.println("5. Open a terminal and execute gradle -v.");
        System.out.println("!!!!!! see cmd_gradle_v.png");

        System.out.println("6. Start the UI with gradle –gui");
        System.out.println("!!!!!! see gradle_gui_has_been_removed.png");

        System.out.println("7. Add a 'startGradle' task that prints &#39;The Gradle is building&#39;.");
        System.out.println("!!!!!! see start_gradle.png");

        System.out.println("8. Add a 'MyDate' task that prints out the current date.");
        System.out.println("!!!!!! see my_date_gradle.png");
    }
}
