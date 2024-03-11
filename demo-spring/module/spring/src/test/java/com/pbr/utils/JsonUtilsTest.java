package com.pbr.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Lawrence Peng
 */
class JsonUtilsTest {

    @Test
    void obj2String() {
        assertThat("a" + 1).isEqualTo("a1");
    }
}