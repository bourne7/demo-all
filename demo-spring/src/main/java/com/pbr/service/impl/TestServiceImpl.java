package com.pbr.service.impl;

import com.pbr.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private ApplicationContext applicationContext;


    @Override
    public void testRedis() {

    }


    @PostConstruct
    public void init() throws Exception {

        MyTestBeanServiceImpl bean = applicationContext.getAutowireCapableBeanFactory().createBean(MyTestBeanServiceImpl.class);

        ((MyTestBeanServiceImpl) bean).test();

    }

}
