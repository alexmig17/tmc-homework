package task2;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

public class Salary extends DefaultTask {

    private int amount = 10;

    @TaskAction
    public void run() {
        System.out.println(String.format("task2.Salary amount is %d", amount));
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
