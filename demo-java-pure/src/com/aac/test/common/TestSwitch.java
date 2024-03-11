package com.aac.test.common;

/**
 * @author Lawrence Peng
 */
public class TestSwitch {

    enum MatchType {
        TYPE_A,
        TYPE_B,
        TYPE_C,
        TYPE_D,
        TYPE_E,
        TYPE_F,
    }

    public static void main(String[] args) {

        for (MatchType matchTypeEnum : MatchType.values()) {
            testSwitchMergeAndNullEnum(matchTypeEnum);
        }

        // throw exception
        testSwitchMergeAndNullEnum(null);


       testVariableScope();

    }

    private static void testSwitchMergeAndNullEnum(MatchType matchTypeEnum) {

        if (matchTypeEnum != null) {
            System.out.print(System.lineSeparator() + matchTypeEnum.ordinal() + " ");
        }

        // cannot pass null to switch
        switch (matchTypeEnum) {
            case TYPE_A:
                System.out.println("[" + matchTypeEnum + "]" + "TYPE_A");
                break;
            case TYPE_B:
                System.out.println("[" + matchTypeEnum + "]" + "TYPE_B");
                break;
            case TYPE_C:
            case TYPE_D:
                System.out.println("[" + matchTypeEnum + "]" + "TYPE_C or TYPE_D");
                break;
            case TYPE_E:
                System.out.println("[" + matchTypeEnum + "]" + "TYPE_E");
                break;
            case TYPE_F:
                System.out.println("[" + matchTypeEnum + "]" + "TYPE_F");
                break;
            default:
                System.out.println("default");
                break;
        }
    }

    private static void testVariableScope() {
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
