package com.aac.test.common;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

public class Java8Base64 {

    public static void main(String[] args) {

        String original = "java8新特性";
        System.out.println("原始字符串: " + original);
        // 使用基本编码
        String base64encodedString = Base64.getEncoder().encodeToString(original.getBytes(StandardCharsets.UTF_8));
        System.out.println("基本加密后: " + base64encodedString);

        // 解码
        byte[] base64decodedBytes = Base64.getDecoder().decode(base64encodedString);
        System.out.println("基本解密后: " + new String(base64decodedBytes, StandardCharsets.UTF_8));

        System.out.println("====");
        // 使用URL编码
        original = "TutorialsPoint?java8";
        System.out.println("URL 原始字符串: " + original);
        base64encodedString = Base64.getUrlEncoder().encodeToString(original.getBytes(StandardCharsets.UTF_8));
        System.out.println("URL 加密后: " + base64encodedString);

        // 使用MIME编码
        System.out.println("====");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 10; ++i) {
            stringBuilder.append(UUID.randomUUID());
        }
        System.out.println("MIME 加密前: " + stringBuilder);
        byte[] mimeBytes = stringBuilder.toString().getBytes(StandardCharsets.UTF_8);
        String mimeEncodedString = Base64.getMimeEncoder().encodeToString(mimeBytes);
        System.out.println("MIME 加密后: " + mimeEncodedString);

    }

}
