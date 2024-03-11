package com.pbr.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Lawrence Peng
 */
//@Component
@Slf4j
public class CommonScheduleTask {

    AtomicInteger atomicInteger = new AtomicInteger(0);

    @Scheduled(fixedDelay = 1000)
    public void scheduleFixedDelayTask() {

        try {
            TimeUnit.SECONDS.sleep(2L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.info(atomicInteger.getAndIncrement() + "");
    }
}
