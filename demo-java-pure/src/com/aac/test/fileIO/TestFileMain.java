package com.aac.test.fileIO;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Scanner;

public class TestFileMain {

    public static void main(String[] args) throws Exception {
        String fileName = "/color_point/gc_4_1_solution.txt";
        File txtFile = createFile(fileName);
//        createFile();
//        useStream(txtFile);
//        usePrintStream(txtFile);
//        useBufferedWriter(txtFile);
//        usePrintWriter(txtFile);
//        useRandomAccessFile(fileNameString);
//        useFileReader(txtFile);
//        useBufferedReader(txtFile);
//        useScanner(txtFile);

        readFileInResource(fileName);
    }

    private static File createFile(String fileName) throws IOException {
        String resourcePath = new File(".").getCanonicalPath() + "/src/main/resources";
        String filePath = resourcePath + fileName;
        File newFile = new File(filePath);
        if (!newFile.isFile()) {
            newFile.createNewFile();
        } else {
            newFile = new File(filePath);
        }
        System.out.println("file is: " + filePath);
        return newFile;
    }

    private static void useStream(File txtFile) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(txtFile, true);
            FileInputStream fileInputStream = new FileInputStream(txtFile);
            for (int i = 0; i < 5; i++) {
                fileOutputStream.write(LocalDateTime.now().toString().getBytes());
                fileOutputStream.write(System.lineSeparator().getBytes());
            }
            int length;
            byte[] bytes = new byte[1024]; // 这种读取的方法是有边界问题的
            while ((length = fileInputStream.read(bytes)) > 0) {
                System.out.print(new String(bytes, 0, length));//将数据变为字符串输出
            }

            fileOutputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void usePrintStream(File txtFile) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(txtFile, true);
            PrintStream printStream = new PrintStream(fileOutputStream);
            for (int i = 0; i < 5; i++) {
                printStream.println(LocalDateTime.now());
            }
            fileOutputStream.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void useBufferedWriter(File txtFile) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(txtFile, true));
            for (int i = 0; i < 5; i++) {
                bufferedWriter.write(LocalDateTime.now().toString());
                bufferedWriter.write(System.lineSeparator());
            }
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // 可以用这个，因为这个方法比较多，构建方法也比较多。
    private static void usePrintWriter(File txtFile) {
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(txtFile, true));
            for (int i = 0; i < 5; i++) {
                printWriter.println(LocalDateTime.now());
            }
            printWriter.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    private static void useRandomAccessFile(String txtFile) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(txtFile, "rw");
            for (int i = 0; i < 5; i++) {
                randomAccessFile.write(LocalDateTime.now().toString().getBytes());
                randomAccessFile.write(System.lineSeparator().getBytes());
            }
            randomAccessFile.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void useFileReader(File txtFile) {
        try {
            FileReader fileReader = new FileReader(txtFile);
            int ch;
            while ((ch = fileReader.read()) != -1) {
                System.out.print((char) ch);
            }
            fileReader.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    // 个人思考，读取的话，最高效率和最安全的方法还应该是要用 useStream 里面的方法，因为涉及到一行超多，一级每行末尾
    // 的问题，所以最好要判断末尾换行问题。
    private static void useBufferedReader(File txtFile) {
        try {
            FileInputStream fileInputStream = new FileInputStream(txtFile);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

            // 构造一个BufferedReader类来读取文件, 输入参数有了不同。
            // BufferedReader br = new BufferedReader(new FileReader(file));

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
            bufferedReader.close();
//            fileInputStream.close();
//            inputStreamReader.close();
            // 关闭了 buffereader以后，剩下2个可以不用关闭了。因为已经自动关闭了。
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void useScanner(File txtFile) {
        try (Scanner scanner = new Scanner(txtFile)) {
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }


    private static void readFileInResource(String relativePath) {
        URL url = TestFileMain.class.getResource(relativePath);
        assert url != null;
        File file = new File(url.getFile());

        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            String line = in.readLine();
            while (line != null) {
                System.out.println(line);
                line = in.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
