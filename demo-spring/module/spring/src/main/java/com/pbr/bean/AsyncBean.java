package com.pbr.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * @author Lawrence Peng
 */
@Slf4j
@Component
public class AsyncBean {

    @Async
    public CompletableFuture<String> asyncMethodWithReturnType() {

        log.info("Execute method asynchronously");

        try {
            Thread.sleep(3000);
            return CompletableFuture.completedFuture("hello world");
        } catch (InterruptedException e) {
            log.error("asyncMethodWithReturnType ", e);
        }

        // Could not return unless InterruptedException happens
        return CompletableFuture.completedFuture("hello world 2");
    }

}
