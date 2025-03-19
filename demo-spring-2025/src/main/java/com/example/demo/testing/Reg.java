package com.example.demo.testing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reg {

    //  https://www.cnblogs.com/lzq198754/p/5780340.html
    //  规则	                正则表达式语法
    //  一个或多个汉字       ^[\u0391-\uFFE5]+$
    //  邮政编码            ^[1-9]\d{5}$
    //  QQ号码              	^[1-9]\d{4,10}$
    //  邮箱                ^[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\.){1,3}[a-zA-z\-]{1,}$
    //  用户名（字母开头 + 数字/字母/下划线）	^[A-Za-z][A-Za-z1-9_-]+$
    //  手机号码	            ^1[3|4|5|8][0-9]\d{8}$
    //  URL	                ^((http|https)://)?([\w-]+\.)+[\w-]+(/[\w-./?%&=]*)?$
    //  18位身份证号	        ^(\d{6})(18|19|20)?(\d{2})([01]\d)([0123]\d)(\d{3})(\d|X|x)?$


    public static void main(String[] args) {
//        testIsMatches();

        testNotInStringGroup();
    }

    /**
     * 不属于某些字符串，需要整个字符串匹配。只能用后向零宽断言。
     */
    private static void testNotInStringGroup() {
        boolean matches1 = "".matches("^(?!CT_1|CT_2)");

        System.out.println(matches1);
    }

    private static void reg1() {
        String str = "basic-example-plugin1-2.4.0.jar";
        String regEx = "(.*)-(\\d+\\.)+jar";
        Pattern pattern = Pattern.compile(regEx);

        Matcher matcher = pattern.matcher(str);

        if (matcher.find()) {
            boolean b = matcher.matches();
            System.out.println(b);
            System.out.println(matcher.toString());
            System.out.println(matcher.group());
            System.out.println(matcher.group(0));
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
        }

    }

    private static void reg2() {
        // 要验证的字符串
        String str = "http://localhost:8080/test/get/page/fruit";
        // 正则表达式规则
        String regEx = ".*/page/.*";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        // 忽略大小写的写法
        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        // 查找字符串中是否有匹配正则表达式的字符/字符串
        boolean rs = matcher.find();
        System.out.println(rs);
    }

    private static void reg3() {
        String str = "dubbo://localhost:20880/com.apple.link.app.request.rpc.TestService:get?version=1.0.0";
        String regEx = "(?<host>dubbo://[0-9a-zA-Z]+:\\d+)/(?<classpath>([0-9a-zA-Z]+\\.)+[0-9a-zA-Z]+):(?<method>\\w+)\\?version=(?<version>\\d+\\.\\d+\\.\\d+)";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(str);
        boolean rs = matcher.find();
        System.out.println(rs);
        System.out.println(matcher.group("host"));
        System.out.println(matcher.group("classpath"));
        System.out.println(matcher.group("method"));
        System.out.println(matcher.group("version"));
    }

    /**
     * matcher.find() 是需要先执行的。
     */
    public static void testReg() {
        String str = "Tx1234";
        String regEx;

        regEx = "(?<host>T.+)";
        regEx = "(T)(.+)";

        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(str);

        boolean rs = matcher.find();

        System.out.println(rs);

        int count = matcher.groupCount();

        for (int i = 0; i <= count; i++) {
            System.out.println("[group " + i + "] -> " + matcher.group(i));
        }

    }


    public static void testIsMatches() {


        String s = "import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;";

        Matcher matcher = Pattern.compile("import\\s.*mybatis").matcher(s);

        System.out.println(matcher.find());

    }


    public static void testJavaExp() {

        String str = "abcgabcgabcd";
        //贪婪型
        System.out.println(str.replaceAll("ab.*g", "***"));
        //勉强型
        System.out.println(str.replaceAll("ab.*?g", "***"));
        //占有型
        System.out.println(str.replaceAll("ab.*+g", "***"));

        //  ***abcd
        //  ******abcd
        //  abcgabcgabcd

        /*
        https://blog.csdn.net/BaymaxCS/article/details/119060722
        * 上述例子为用正则表达式匹配：字母 ab 开头，中间有零或多个字符，最后以字母 g 结尾的字符，再将它们替换成 “***”。

        贪婪型：ab.*g
        尽可能匹配满足条件的字符串，第一次发现 abcg 满足 ab.*g 后，继续往后匹配至 “abcgabcg” ，发现仍满足需求，继续匹配字符串至"abcgabcgabcd"，发现不满足需求，返回上一次满足需求的结果，故最终替换的字符串为 “abcgabcg”，最终输出的结果为 ：***abcd 。

        勉强型：ab.*?g
        当发现 “abcg” 满足 ab.*g 后立刻停止匹配，字符串 “abcgabcgabcd” 有两个 “abcg” 满足条件，故替换2次，最后输出的结果为：******abcd 。

        占有型：ab.*+g
        类似于贪婪型，第一次发现 “abcg” 满足 ab.*g 后，继续往后匹配至 “abcgabcg” ，发现仍满足需求，继续匹配字符串至 “abcgabcgabcd” ，发现正则表达式不成立，又因占有型无回溯功能，正则表达式校验失败，最终不会替换任何字符，输出原字符：abcgabcgabcd 。
        *
        */


    }
}
