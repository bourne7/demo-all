package com.pbr.dao.rdb.helper;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * 用于数据库对象的映射。这个映射除了包含了时间字段。
 */
@Data
@MappedSuperclass
public class EntityTime {

    @Column(nullable = false, columnDefinition = "bigint default 0")
    @CreatedDate
    private long createTime;

    @Column(nullable = false, columnDefinition = "bigint default 0")
    @LastModifiedDate
    private long updateTime;
}
