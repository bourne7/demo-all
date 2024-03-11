package com.pbr.controller;

import com.pbr.dao.rdb.entity.TaFruit;
import com.pbr.service.fruit.inter.TestFruitService;
import com.pbr.service.fruit.inter.TestService;
import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("demo")
@Validated
public class TestFruitController {


    @Autowired
    EntityManager entityManager;

    @Autowired
    TestFruitService testFruitService;

    @Autowired
    TestService testService;

    @RequestMapping(value = "get", method = RequestMethod.GET)
    public Object get(@RequestParam(required = false) String code) {
        return testFruitService.get(code);
    }

    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public Object delete(@RequestParam @NotBlank String code) {
        List<TaFruit> taFruits = testFruitService.get(code);
        testFruitService.delete(code);
        return taFruits;
    }

    @RequestMapping(value = "createOrUpdate", method = RequestMethod.POST)
    public Object createOrUpdate(@RequestBody TaFruit taFruit) {
        testFruitService.createOrUpdate(taFruit);
        return taFruit;
    }


}
