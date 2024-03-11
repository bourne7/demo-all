package com.pbr.service.fruit.impl;

import com.google.common.collect.Lists;
import com.pbr.dao.rdb.entity.TaFruit;
import com.pbr.dao.rdb.repository.TaFruitRepository;
import com.pbr.service.fruit.inter.TestFruitService;
import com.pbr.utils.JsonUtils;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author pbr
 */
@Service
@Slf4j
public class TestFruitServiceImpl implements TestFruitService {

    private static final AtomicInteger atomInteger = new AtomicInteger();

    @Autowired
    TaFruitRepository taFruitRepository;

    @Autowired
    EntityManager entityManager;

    @PostConstruct
    public void init() {

        taFruitRepository.deleteAll();

        for (int i = 0; i < 100; i++) {
            TaFruit fruit = new TaFruit();
            fruit.setCode("apple_" + i);
            taFruitRepository.save(fruit);
        }
    }

    @Override
    public List<TaFruit> get(String code) {
        if (ObjectUtils.isEmpty(code)) {
            return taFruitRepository.findAll();
        }
        return Lists.newArrayList(taFruitRepository.findByCode(code));
    }

    @Transactional
    @Override
    public void delete(String code) {

        log.info(JsonUtils.obj2String(taFruitRepository.findByCode(code)));

        if (!TransactionSynchronizationManager.isActualTransactionActive()) {
            throw new RuntimeException("No transaction!");
        }

        taFruitRepository.deleteByCode(code);

        // 注意这里并不会出现可重复读
        log.info(JsonUtils.obj2String(taFruitRepository.findByCode(code)));

        entityManager.flush();
        // 这里同样不会出现可重复读
        log.info(JsonUtils.obj2String(taFruitRepository.findByCode(code)));
    }


    @Transactional(transactionManager = "transactionManager", timeout = 36000, rollbackFor = Exception.class)
    @Override
    public void createOrUpdate(TaFruit taFruit) {

        if (!TransactionSynchronizationManager.isActualTransactionActive()) {
            throw new RuntimeException("No transaction!");
        }

        TaFruit byName = taFruitRepository.findByCode(taFruit.getCode());

        if (byName != null) {

        }


        taFruitRepository.save(taFruit);

        // 如果没有这一句的话, 则不会即时刷新到 数据库, 那么报错只会发生在事务提交的一瞬间. 如果加了这一句, 那么就会在过程中报错.
        // entityManager.flush();

        log.info("taFruit {}", JsonUtils.obj2String(taFruit));

        // 注意这里不会让 taFruit 被 entityManager 管理
        taFruit.setCode("after saving");
    }
}
