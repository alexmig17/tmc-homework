package task4.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ClassForTest1Test {

    @Test
    public void doSomething() throws InterruptedException {
        ClassForTest1 classForTest1 = new ClassForTest1();
        String actual = classForTest1.doSomething();
        Thread.sleep(10000);
        assertEquals("something", actual);
    }
}