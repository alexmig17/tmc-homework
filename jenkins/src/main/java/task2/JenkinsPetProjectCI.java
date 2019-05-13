package task2;

/**
 * class with implemented task2:
 * 1. Create GitHub account
 * 2. Install Jenkins
 * 3. Install SonarQube
 * a. Configure Jenkins to use local SonarQube installation.
 * b. Configure custom SonarQube quality profile.
 * 4. Fork Spring Petclinic application (https://github.com/spring-projects/spring-petclinic/)
 * 5. Configure Jenkins build job to checkout Petclinic project from your GitHub account
 * 6. Configure polling interval (5 minutes)
 * 7. Setup Petclinic build with Maven
 * 8. Ensure unit tests are executed
 * 9. Ensure unit test results are display on Jenkins project and build pages
 * 10. Commit any change to your GitHub project and this should result in Jenkins
 * automated build triggered
 * 11. For every push to repository Jenkins should build the build when the following
 * statement are true:
 * A. Sonar has been run.
 * B. JUnit tests has been run.
 * C. if PointA and PointA are failed, then the build must be failed too.
 * In this case the pusher should get the notification with the report.
 * 12. If the build is failed, send email to the authors of last commits with the reason of fail
 * 13. Add ability to generate a version number for each release. It should be incremented
 * since start of the year, for example 1.0.2, where 2 is the second build since the start
 * of the current year.
 */
public class JenkinsPetProjectCI {

    /**
     * main method for run task2
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
        System.out.println("See screenshots at resources/task2 folder");
        System.out.println("1. Create GitHub account");
        System.out.println("created_git_hub_account.png");
        System.out.println("2. Install Jenkins");
        System.out.println("installed_jenkins.png");
        System.out.println("3. Install SonarQube");
        System.out.println("installed_sonar_qube.png");
        System.out.println("a. Configure Jenkins to use local SonarQube installation.");
        System.out.println("jenkins_sonar_qube_configuration.png");
        System.out.println("b. Configure custom SonarQube quality profile.");
        System.out.println("sonar_qube_change_default_rule.png");
        System.out.println("sonar_qube_my_quality_profile.png");
        System.out.println("4. Fork Spring Petclinic application (https://github.com/spring-projects/spring-petclinic/)");
        System.out.println("git_hub_forked_pet_repository.png");
        System.out.println("5. Configure Jenkins build job to checkout Petclinic project from your GitHub account");
        System.out.println("jenkins_git_configuration_for_checkout_pet_project.png");
        System.out.println("6. Configure polling interval (5 minutes)");
        System.out.println("jenkins_poll_scm_5_minutes.png");
        System.out.println("7. Setup Petclinic build with Maven");
        System.out.println("jenkins_maven_build_configuration.png");
        System.out.println("8. Ensure unit tests are executed");
        System.out.println("Ensure_that_tests_run_during_build.png");
        System.out.println("9. Ensure unit test results are display on Jenkins project and build pages");
        System.out.println("test_results_on_build_page.png");
        System.out.println("test_results_on_project_page.png");
        System.out.println("10. Commit any change to your GitHub project and this should result in Jenkins\n" +
                " * automated build triggered");
        System.out.println("scm_trigger_executing_example.png");
        System.out.println("11. For every push to repository Jenkins should build the build when the following\n" +
                " * statement are true:");
        System.out.println("auto build on push event configuration");
        System.out.println("git_hub_personal_access_token_for_web_hook_configuration.png");
        System.out.println("git_hub_project_web_hook_configuration.png");
        System.out.println("jenkins_configure_build_on_push_event_git_hub_hook.png");
        System.out.println("jenkins_server_authentication_token_for_git_hub_web_hook_configuration.png");
        System.out.println("A. Sonar has been run.");
        System.out.println("See screenshots in 'C' point");
        System.out.println("B. JUnit tests has been run.");
        System.out.println("JUnit run with mvn clean package:");
        System.out.println("jenkins_maven_build_configuration.png");
        System.out.println("C. if PointA and PointA are failed, then the build must be failed too.\n" +
                " * In this case the pusher should get the notification with the report.");
        System.out.println("if you push something that will cause of broken build  - you'll received email notification. see 12 point");
        System.out.println("jenkins_configuration_for_run_sonar_after_success_build.png");
        System.out.println("jenkins_enable_prepare_sonar_scanner_environment.png");
        System.out.println("jenkins_sonar_scanner_configuration.png");
        System.out.println("12. If the build is failed, send email to the authors of last commits with the reason of fail");
        System.out.println("jenkins_email_configuration.png");
        System.out.println("13. Add ability to generate a version number for each release. It should be incremented\n" +
                " * since start of the year, for example 1.0.2, where 2 is the second build since the start\n" +
                " * of the current year.");
        System.out.println("jenkins_build_version_variable_configuration.png");
        System.out.println("jar_with_version.png");
        System.out.println("maven_pom_build_version_configuration.png");
    }
}
