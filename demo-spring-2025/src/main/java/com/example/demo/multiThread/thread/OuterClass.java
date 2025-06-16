package com.example.demo.multiThread.thread;

public class OuterClass {

    private int outer_int = 1;

    private class InnerClass {
        private int inner_int = 2222;

        private void print() {
            System.out.println("内部打印：outer_int = " + outer_int);
            System.out.println("内部打印：inner_int = " + inner_int);
            System.out.println();
        }
    }

    private class InnerClass2 {
        public int inner_int2 = 3;

        public void print() {
            InnerClass innerClass = new InnerClass();
            innerClass.print();
        }
    }

    private void print() {
        System.out.println("外部打印：outer_int = " + outer_int);
    }

    public static void main(String[] args) {
        OuterClass outerClass = new OuterClass();
        outerClass.print();

        InnerClass innerClass = outerClass.new InnerClass();
        System.out.println("外部打印：inner_int = " + innerClass.inner_int);

        innerClass.print();

        InnerClass2 innerClass2 = outerClass.new InnerClass2();
        innerClass2.print();
    }

}
