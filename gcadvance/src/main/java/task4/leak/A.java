package task4.leak;

/**
 * class A for static variable memory leak example
 */
public class A {

    private static final int[] memoryLeakItAlwaysInMemoryAndNotUsed = new int[2000000];

    public int[] getVariable() {
        return memoryLeakItAlwaysInMemoryAndNotUsed;
    }
}
