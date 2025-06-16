package com.aac.test.common;

/**
 * @author Lawrence Peng
 */
public class TestException {

    public static void main(String[] args) {

        String str = "a";

        try {
            // 抛出异常之前的代码，仍然有效。这里并没有类似事务的回滚。
            str = "b";

            if (str.equals("b")) {
                throw new Error("test");
            }

        } catch (Throwable e) {
            e.printStackTrace();
            System.out.println("catch " + str);
        } finally {
            System.out.println("finally " + str);
        }

        System.out.println("main" + str);

    }
}
