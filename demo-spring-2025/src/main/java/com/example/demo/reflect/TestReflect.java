package com.example.demo.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestReflect {

    public static void main(String[] args) {
        try {
            TestReflect testReflect = new TestReflect();
            testReflect.callMethod();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * https://www.cnblogs.com/qqzy168/p/3622712.html
     * public Method[] getMethods()返回某个类的所有公用（public）方法包括其继承类的公用方法，当然也包括它所实现接口的方法。
     * public Method[] getDeclaredMethods()对象表示的类或接口声明的所有方法，包括公共、保护、默认（包）访问和私有方法，但不包括继承的方法。当然也包括它所实现接口的方法。
     */
    public void callMethod() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        // 反射调用
        System.out.println(System.lineSeparator() + "[1]");
        Class classType = Class.forName("reflect.Car");
        Object object = classType.newInstance();
        InterfaceTransport interfaceTransport = (InterfaceTransport) object;
        System.out.println(interfaceTransport.info());

        // 获取公有方法
        System.out.println(System.lineSeparator() + "[2]");
        Method method = classType.getMethod("info", null);
        String methodCallResult = (String) method.invoke(interfaceTransport);
        System.out.println(methodCallResult);

        // 获取私有方法
        System.out.println(System.lineSeparator() + "[3]");
        method = classType.getDeclaredMethod("infoPrivate", null);
        method.setAccessible(true); // 抑制Java的访问控制检查
        methodCallResult = (String) method.invoke(interfaceTransport);
        System.out.println(methodCallResult);

        // 获取 接口 default 方法
        System.out.println(System.lineSeparator() + "[4]");
        method = classType.getMethod("defaultInfo", null);
        methodCallResult = (String) method.invoke(interfaceTransport);
        System.out.println(methodCallResult);

        // 获取 接口 static 方法
        System.out.println(System.lineSeparator() + "[5]");
        System.out.println("直接调用接口静态方法。");
        System.out.println(InterfaceTransport.staticInfo());
        System.out.println("通过反射调用。");
        method = InterfaceTransport.class.getMethod("staticInfo", null);
        methodCallResult = (String) method.invoke(null);
        System.out.println(methodCallResult);
    }

}
