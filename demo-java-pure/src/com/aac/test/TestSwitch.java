package com.aac.test;

/**
 * @author Lawrence Peng
 */
public class TestSwitch {

    public static void main(String[] args) {

        String str = "1";

        // https://stackoverflow.com/questions/3894119/variables-scope-in-a-switch-case
        // The scope of the variables in each case clause corresponds to the whole switch statement.
        boolean result = true;
        switch (str) {
            case "1":
                result = false;
                System.out.println("1");
                System.out.println(result);
                break;
            case "2":
                result = true;
                System.out.println("2");
                break;
            default:
                System.out.println("default");
                break;
        }

    }

}
