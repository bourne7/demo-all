package com.pbr.service.impl;

import com.pbr.service.MyTestBeanService;
import com.pbr.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Lawrence Peng
 */
@Slf4j
public class MyTestBeanServiceImpl implements MyTestBeanService {

    @Autowired
    private CrudServiceImpl crudService;

    @Override
    public void test() {

        log.info("crudService: {}", crudService);

    }
}
