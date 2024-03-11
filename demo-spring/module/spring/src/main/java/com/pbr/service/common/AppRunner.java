package com.pbr.service.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author Lawrence Peng
 */
@Slf4j
@Component
public class AppRunner implements CommandLineRunner {


    @Override
    public void run(String... args) throws Exception {

        log.info("Running in CommandLineRunner");

//        while (true) {
//            log.info(new Date().toString());
//            TimeUnit.SECONDS.sleep(1L);
//        }

    }
}
