package com.epam.task3;

/**
 * Task 3. (10 points) Test coverage
 * Take your pet/mentoring application from previous performed tasks.
 * 1. Install Cobertura as plugin for IDE or maven.
 * 2. Check tests coverage.
 * 3. Add tests to get 90% test coverage at min
 */
public class TestCoverage {

    /**
     * main for task 3
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
        System.out.println("See resources/task3 folder");
        System.out.println("Coverage by intellij idea:");
        System.out.println("idea_tes_coverage.png");
        System.out.println("Coverage by Cobertura:");
        System.out.println("Cobertura_1.png");
        System.out.println("Cobertura_2.png");
    }
}
