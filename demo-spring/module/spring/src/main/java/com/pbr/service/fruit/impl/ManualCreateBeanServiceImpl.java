package com.pbr.service.fruit.impl;

import com.pbr.service.fruit.inter.ManualCreateBeanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Lawrence Peng
 */
@Slf4j
public class ManualCreateBeanServiceImpl implements ManualCreateBeanService {

    @Autowired
    private TestFruitServiceImpl crudService;

    @Override
    public void test() {

        log.info("crudService: {}", crudService);

    }
}
