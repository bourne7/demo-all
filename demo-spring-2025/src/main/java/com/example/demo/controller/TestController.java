package com.example.demo.controller;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("test")
@Slf4j
public class TestController {

    AtomicInteger count = new AtomicInteger(0);

    @PostConstruct
    public void init() {
        test();
    }

    @Scheduled(fixedRate = 1000, timeUnit = TimeUnit.MINUTES)
    @RequestMapping(value = "test", method = RequestMethod.GET)
    public Object test() {
        int i = count.incrementAndGet();
        log.info("count {}", i);
        return i;
    }

}
