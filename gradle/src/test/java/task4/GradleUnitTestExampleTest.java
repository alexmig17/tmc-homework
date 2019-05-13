package task4;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GradleUnitTestExampleTest {

    @Test
    public void utilMethod() throws InterruptedException {
        Thread.sleep(2000);
        String actual = GradleUnitTestExample.utilMethod();
        assertEquals("test1", actual);
    }

    @Test
    public void utilMethod2() throws InterruptedException {
        Thread.sleep(2000);
        String actual = GradleUnitTestExample.utilMethod2();
        assertEquals("test2", actual);
    }

    @Test
    public void utilMethod3() throws InterruptedException {
        Thread.sleep(2000);
        String actual = GradleUnitTestExample.utilMethod3();
        assertEquals("test3", actual);
    }
}