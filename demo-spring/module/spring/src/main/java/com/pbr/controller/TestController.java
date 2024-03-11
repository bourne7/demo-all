package com.pbr.controller;

import com.pbr.bean.AsyncBean;
import com.pbr.dto.FruitDTO;
import com.pbr.service.fruit.inter.TestService;
import com.pbr.utils.CommonUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("test")
@Slf4j
public class TestController {

    @Autowired
    TestService testService;

    @RequestMapping(value = "test", method = RequestMethod.GET)
    public Object test() {
        testService.test();
        return LocalDateTime.now().toString();
    }

    @Autowired
    AsyncBean asyncBean;

    @RequestMapping(value = "testAsyncMethod", method = RequestMethod.GET)
    public Object testAsyncMethod() {

        CompletableFuture<String> stringCompletableFuture = asyncBean.asyncMethodWithReturnType();

        stringCompletableFuture.thenAccept(s -> log.info("async result:{}", s));

        log.info("stringCompletableFuture.isDone(): {}", stringCompletableFuture.isDone());

        return stringCompletableFuture.isDone();
    }


    @RequestMapping(value = "testAsyncRequest", method = RequestMethod.GET)
    @Async
    public CompletableFuture<FruitDTO> testAsyncRequest(HttpServletRequest request) {

        log.info("remote address {}", request.getRemoteAddr());

        CommonUtils.sleep(3);

        CompletableFuture<FruitDTO> result = CompletableFuture.completedFuture(FruitDTO.getNewInstance());

        log.info("testAsyncRequest is done");

        return result;
    }

    @RequestMapping(value = "testPublishEvent", method = RequestMethod.GET)
    public Object testPublishEvent() {
        testService.publishEvent();
        return LocalDateTime.now().toString();
    }

}
