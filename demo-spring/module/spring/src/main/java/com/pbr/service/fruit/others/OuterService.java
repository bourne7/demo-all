package com.pbr.service.fruit.others;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author Lawrence Peng
 */
public class OuterService {

    @Component
    public static class InnerSpringBean {

        public String getInfo() {
            return LocalDateTime.now().toString();
        }
    }

}
