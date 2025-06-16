package com.example.demo.testing;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author Lawrence Peng
 */
public class Process {

    public static void main(String[] args) throws InterruptedException, IOException {

        ProcessBuilder builder = new ProcessBuilder("notepad.exe");

        java.lang.Process process = builder.start();

        TimeUnit.SECONDS.sleep(3L);

        process.destroy();

    }

}
