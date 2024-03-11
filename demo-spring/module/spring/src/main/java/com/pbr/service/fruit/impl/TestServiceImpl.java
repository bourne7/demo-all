package com.pbr.service.fruit.impl;

import com.pbr.mq.MyEvent;
import com.pbr.service.fruit.inter.TestService;
import com.pbr.service.fruit.others.OuterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void test() {
        log.info("Hello World!");
    }

    @Override
    public void publishEvent() {

        applicationEventPublisher.publishEvent(MyEvent.builder().build());

        log.info("Ending test");

    }

    @Autowired
    private OuterService.InnerSpringBean innerSpringBean;


    @PostConstruct
    public void init() throws Exception {

        ManualCreateBeanServiceImpl bean = applicationContext.getAutowireCapableBeanFactory().createBean(ManualCreateBeanServiceImpl.class);

        ((ManualCreateBeanServiceImpl) bean).test();

        log.info("after manual create bean");


        log.info(innerSpringBean.getInfo());

    }

}
