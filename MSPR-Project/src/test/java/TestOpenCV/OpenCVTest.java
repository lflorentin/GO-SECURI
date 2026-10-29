package TestOpenCV;

import org.junit.Test;

public class OpenCVTest {
    @Test
    public void someOpenCVTest() {
        System.out.printf("java.library.path: %s%n",
                System.getProperty("java.library.path"));
        System.loadLibrary("opencv_320.jar");
    }
}