package com.pbr.controller;

import com.pbr.dto.FruitDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("test")
@Slf4j
public class TestController {
    @RequestMapping(value = "method1", method = RequestMethod.POST)
    public Object method1(@RequestBody String body) {

        log.info(body);

        FruitDTO fruitDTO = new FruitDTO();
        fruitDTO.setFruitId(Long.MAX_VALUE);
        fruitDTO.setCreateTime(new Date());

        return fruitDTO;
    }


    @RequestMapping(value = "method2", method = RequestMethod.POST)
    public Object method2(@RequestBody FruitDTO body) {
        log.info(body.toString());
        return body;
    }

}
