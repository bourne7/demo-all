package com.example.demo.reflect.invokemethod;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class TestMethodHandler {


    class GrandFather {
        void thinking() {
            System.out.println("I am grandfather.");
        }
    }

    class Father extends GrandFather {
        @Override
        void thinking() {
            System.out.println("I am father.");
        }
    }

    /**
     * This method is caller sensitive, which means that it may return different values to different callers.
     * For any given caller class C, the lookup object returned by this call has equivalent capabilities to any lookup object supplied by the JVM to the bootstrap method of an invokedynamic instruction executing in the same caller class C.
     *
     * 这里就是说，深入理解 JVM 书里面的方法也失效了。
     */
    class Son extends Father {
        @Override
        void thinking() {
            try {
                MethodType mt = MethodType.methodType(void.class);
                MethodHandle mh = MethodHandles.lookup().findSpecial(GrandFather.class, "thinking", mt, getClass());
                mh.invoke(this);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        new TestMethodHandler().new Son().thinking();

    }
}
