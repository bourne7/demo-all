package com.pbr.service.fruit.inter;

import com.pbr.dao.rdb.entity.TaFruit;

import java.util.List;

/**
 * @author pbr
 */
public interface TestFruitService {

    List<TaFruit> get(String code);

    void delete(String code);

    void createOrUpdate(TaFruit taFruit);
}
