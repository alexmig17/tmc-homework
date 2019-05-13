package task1;

/**
 * class with implemented task1:
 * Task 1. (20 points) Play with Jenkins
 * 1. Download LTS version of Jenkins
 * 2. Install Jenkins (as a service)
 * 3. Install JDK7, Maven, Ant
 * 4. Configure Jenkins with installed JDK, Maven, Ant
 * 5. Configure Jenkins with additional JDK (auto-installed)
 * 6. Install 10 Jenkins plugins (Git, Parametrized trigger, email-ext, Scriptler, Groovy
 * postbuild, Green Balls etc.)
 * 7. Configure Jenkins security (install Role strategy plugin). Remove anonymous access.
 * Create administrator user (all permissions) and developer user (build job, cancel
 * builds)
 * 8. Create simple free-style job
 * 9. Setup simple trigger and build action (echo current build number)
 * 10. Send email about build result
 */
public class JenkinsFirstFreeStyleProject {

    /**
     * main method for run task1
     *
     * @param args no custom args
     */
    public static void main(String[] args) {
        printMenu();
    }

    /**
     * print menu
     */
    private static void printMenu() {
        System.out.println("Task 1. (20 points) Play with Jenkins");
        System.out.println("see results in resources/task1 folder");
        System.out.println("1. Download LTS version of Jenkins");
        System.out.println("2. Install Jenkins (as a service)");
        System.out.println("See screenshot jenkins_as_windows_server.png");
        System.out.println("3. Install JDK7, Maven, Ant");
        System.out.println("4. Configure Jenkins with installed JDK, Maven, Ant");
        System.out.println("See screenshot jdk_configuration.png");
        System.out.println("See screenshot maven_configuration.png");
        System.out.println("See screenshot ant_configuration.png");
        System.out.println("5. Configure Jenkins with additional JDK (auto-installed)");
        System.out.println("See screenshot jdk_autoconfigure.png");

        System.out.println("6. Install 10 Jenkins plugins (Git, Parametrized trigger, email-ext, Scriptler, Groovy\n" +
                " * postbuild, Green Balls etc.)");
        System.out.println("See screenshot jenkins_plugins.png");
        System.out.println("7. Configure Jenkins security (install Role strategy plugin). Remove anonymous access.\n" +
                " Create administrator user (all permissions) and developer user (build job, cancel\n" +
                " builds)");
        System.out.println("See screenshot jenkins_roles.png");
        System.out.println("See screenshot jenkins_users.png");
        System.out.println("See screenshot jenkins_assigned_roles_to_users.png");
        System.out.println("8. Create simple free-style job");
        System.out.println("9. Setup simple trigger and build action (echo current build number)");
        System.out.println("See screenshot jenkins_item_configuration_trigger_build_and_git.png");
        System.out.println("See screenshot git_configuration.png");
        System.out.println("See screenshot jenkins_build_result.png");
        System.out.println("10. Send email about build result");
        System.out.println("See screenshot jenkins_smpt_configuration.png");
        System.out.println("See screenshot jenkins_email_notification.png");
        System.out.println("See screenshot jenkins_post_build_action_always_send_email.png");
    }
}
