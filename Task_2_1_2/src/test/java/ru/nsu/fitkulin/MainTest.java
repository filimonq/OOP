package ru.nsu.fitkulin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import ru.nsu.fitkulin.Master.Master;
import ru.nsu.fitkulin.Worker.Worker;
import java.io.*;

public class MainTest {

    @Test
    public void testWithNonPrimeNumbers() throws InterruptedException {
        long[] numbers = {11, 13, 17, 19, 23, 29, 31, 37, 41, 43};
        int workerCount = 2;

        Master master = new Master();
        master.numbers = numbers;
        master.workerCount = workerCount;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Thread masterThread = new Thread(master::start);
        masterThread.start();

        Thread.sleep(100);

        for (int i = 0; i < workerCount; i++) {
            Thread workerThread = new Thread(() -> Worker.main(new String[]{}));
            workerThread.start();
        }

        masterThread.join();

        String output = outputStream.toString();
        assertTrue(output.contains("Final result: false"));

        System.setOut(System.out);
    }
}