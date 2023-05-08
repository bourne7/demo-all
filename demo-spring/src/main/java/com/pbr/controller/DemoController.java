package com.pbr.controller;

import com.google.common.collect.Lists;
import com.pbr.dao.mongo.entity.MongoFruit;
import com.pbr.dao.mongo.repository.KsBinSpaceUpdateRecordRepository;
import com.pbr.dao.mongo.repository.MongoFruitRepository;
import com.pbr.dao.mysql.entity.MysqlFruit;
import com.pbr.dao.mysql.repository.MysqlFruitRepository;
import com.pbr.service.CrudService;
import com.pbr.service.TestService;
import jakarta.persistence.EntityManager;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("demo")
public class DemoController {

    @Autowired
    MongoFruitRepository mongoFruitRepository;
    @Autowired
    MysqlFruitRepository mysqlFruitRepository;
    @Autowired
    EntityManager entityManager;
    @Autowired
    private CrudService crudService;
    @Autowired
    private TestService testService;
    @Autowired
    private KsBinSpaceUpdateRecordRepository ksBinSpaceUpdateRecordRepository;

    @RequestMapping(value = "get1", method = RequestMethod.GET)
    public Object get1() {
        System.out.println(1);
        return new Date() + " - get1";
    }

    @RequestMapping(value = "get2", method = RequestMethod.GET)
    public Object get2() {
        return new Date() + " - get2";
    }

    @RequestMapping(value = "post1", method = RequestMethod.POST)
    public Object post1() {
        return new Date() + " - post1";
    }

    @RequestMapping(value = "post2", method = RequestMethod.POST)
    public Object post2() {
        return new Date() + " - post2";
    }

    @RequestMapping(value = "mongoFruit", method = RequestMethod.GET)
    public Object mongoFruit(@RequestParam(required = false) String name) {

        mongoFruitRepository.save(new MongoFruit(name));

        return mongoFruitRepository.findAll();
    }

    @RequestMapping(value = "getMysqlFruit", method = RequestMethod.GET)
    public Object getMysqlFruit(@RequestParam(required = false) String name) {

        List<MysqlFruit> result;

        if (ObjectUtils.isEmpty(name)) {
            result = mysqlFruitRepository.findAll();
        } else {
            result = Lists.newArrayList(mysqlFruitRepository.findByName(name));
        }
        return result;

    }

    @RequestMapping(value = "createMysqlFruit", method = RequestMethod.GET)
    public Object createMysqlFruit(@RequestParam String name) {

        MysqlFruit mysqlFruit = new MysqlFruit();
        mysqlFruit.setName(name);

        mysqlFruitRepository.save(mysqlFruit);

        return mysqlFruitRepository.findAll();
    }

    @RequestMapping(value = "deleteFruit", method = RequestMethod.GET)
    public Object deleteFruit(@RequestParam String name) {

        crudService.deleteFruit(name);

        return LocalDateTime.now().toString();
    }

    @RequestMapping(value = "updateFruitByName", method = RequestMethod.GET)
    public Object updateFruitByName(@RequestParam String name) {

        crudService.updateFruit(name);

        return LocalDateTime.now().toString();
    }

    @RequestMapping(value = "updateFruitEntity", method = RequestMethod.POST)
    public Object updateFruitEntity(@RequestBody MysqlFruit mysqlFruit) {

        crudService.updateFruitEntity(mysqlFruit);

        return LocalDateTime.now().toString();
    }

    @RequestMapping(value = "testRedis", method = RequestMethod.GET)
    public Object testRedis() {
        testService.testRedis();
        return LocalDateTime.now().toString();
    }

    @RequestMapping(value = "testPublishEvent", method = RequestMethod.GET)
    public Object testPublishEvent() {
        crudService.publishEvent();
        return LocalDateTime.now().toString();
    }
}
