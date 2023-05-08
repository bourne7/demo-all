package com.pbr.service;

import com.pbr.dao.mysql.entity.MysqlFruit;

/**
 * @author pbr
 */
public interface CrudService {

    /**
     * test
     */
    void publishEvent();

    /**
     * @param name name
     */
    void deleteFruit(String name);


    /**
     * @param name name
     */
    void updateFruit(String name);


    void updateFruitEntity(MysqlFruit mysqlFruit);
}
