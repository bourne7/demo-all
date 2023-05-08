package com.pbr.service.impl;

import com.pbr.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class TestServiceImpl implements TestService {

    @Override
    public void testRedis() {

    }


    @PostConstruct
    public void init() throws Exception {

//        while (true) {
//            log.info(new Date().toString());
//            TimeUnit.SECONDS.sleep(1L);
//        }

    }

}
