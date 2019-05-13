package task2;

/**
 * task2:
 * Write a custom task class of type Salary. Add a property ‘amount’ for the size of salary.
 * Assign a default value to it. The task action should print the ‘amount’ value property.
 * Add two tasks (MySalary and Mom&#39;s Friend&#39;s Son’s Salary) to your build script, both of type
 * Salary. One should assign a custom value to the ‘amount’ property. Execute both tasks.
 */
public class GradleCustomTask {

    /**
     * implementation for task 2
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
        System.out.println("See screenshots from resources/task2 folder");
        System.out.println("See my Salary result in my_salary_gradle.png");
        System.out.println("See Friends salary result in friends_salary_gradle.png");
    }
}
