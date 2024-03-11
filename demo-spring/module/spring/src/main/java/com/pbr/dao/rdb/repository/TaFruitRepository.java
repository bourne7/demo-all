package com.pbr.dao.rdb.repository;

import com.pbr.dao.rdb.entity.TaFruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author pbr
 */
@Repository
public interface TaFruitRepository extends JpaRepository<TaFruit, Long>, JpaSpecificationExecutor<TaFruit> {

    void deleteByCode(String code);

    TaFruit findByCode(String code);

    /**
     * Modifying
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update TaFruit set size = :size where code = :code")
    int updateFruit(String code, Integer size);

}
