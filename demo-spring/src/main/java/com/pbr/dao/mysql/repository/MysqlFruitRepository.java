package com.pbr.dao.mysql.repository;

import com.pbr.dao.mysql.entity.MysqlFruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author pbr
 */
@Repository
public interface MysqlFruitRepository extends JpaRepository<MysqlFruit, Long>,
        JpaSpecificationExecutor<MysqlFruit> {
    void deleteByName(String name);

    MysqlFruit findByName(String name);

    /**
     * Modifying
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update MysqlFruit set size = :size, updateTime = :updateTime where name = :name")
    int updateFruit(Integer size, Date updateTime, String name);

}
