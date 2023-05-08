package commonTest;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author Lawrence Peng
 */
public class TestProcess {

    public static void main(String[] args) throws InterruptedException, IOException {

        ProcessBuilder builder = new ProcessBuilder("notepad.exe");

        Process process = builder.start();

        TimeUnit.SECONDS.sleep(3L);

        process.destroy();

    }

}
