package com.example.demo.reflect.invokemethod;

import java.lang.invoke.*;

public class TestInvoke {

    public static void main(String[] args) throws Throwable{

        TestInvoke testInvoke = new TestInvoke();

        testInvoke.invokeExact();

    }

    public void invokeExact() throws Throwable {
        MethodType methodType = MethodType.methodType(String.class, int.class, int.class);
        MethodHandle methodHandle = MethodHandles.lookup().findVirtual(String.class, "substring", methodType);
        String str = (String) methodHandle.invokeExact("Hello World", 1, 3);

        System.out.println(str);
    }


    /**
     * https://blog.csdn.net/yushuifirst/article/details/48028859
     */
    public void useConstantCallSite() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType type = MethodType.methodType(String.class, int.class, int.class);
        MethodHandle mh = lookup.findVirtual(String.class, "substring", type);
        ConstantCallSite callSite = new ConstantCallSite(mh);
        MethodHandle invoker = callSite.dynamicInvoker();
        String result = (String) invoker.invoke("Hello", 2, 3);
    }


    public void useMutableCallSite() throws Throwable {
        MethodType type = MethodType.methodType(int.class, int.class, int.class);
        MutableCallSite callSite = new MutableCallSite(type);
        MethodHandle invoker = callSite.dynamicInvoker();
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle mhMax = lookup.findStatic(Math.class, "max", type);
        MethodHandle mhMin = lookup.findStatic(Math.class, "min", type);
        callSite.setTarget(mhMax);
        int result = (int) invoker.invoke(3, 5); //值为5
        callSite.setTarget(mhMin);
        result = (int) invoker.invoke(3, 5); //值为3
    }

//    public class ToUpperCase {
//        public static CallSite bootstrap(Lookup lookup, String name, MethodType type, String value) throws Exception {
//            MethodHandle mh = lookup.findVirtual(String.class, "toUpperCase", MethodType.methodType(String.class)).bindTo(value);
//            return new ConstantCallSite(mh);
//        }
//    }




}
