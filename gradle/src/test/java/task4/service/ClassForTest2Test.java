package task4.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ClassForTest2Test {

    @Test
    public void doSomething() throws InterruptedException {
        ClassForTest2 classForTest2 = new ClassForTest2();
        String actual = classForTest2.doSomething();
        Thread.sleep(10000);
        assertEquals("something", actual);
    }
}