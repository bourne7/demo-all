package com.pbr.java;

import lombok.extern.slf4j.Slf4j;

import java.util.stream.IntStream;

/**
 * @author Lawrence Peng
 */
@Slf4j
public class TestMain {

    public static void main(String[] args) {
        IntStream.range(1, 100).forEach(i -> log.info("Test" + i));
    }
}
