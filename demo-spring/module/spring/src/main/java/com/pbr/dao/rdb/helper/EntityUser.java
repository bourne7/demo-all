package com.pbr.dao.rdb.helper;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用于数据库对象的映射。这个映射除了包含了用户字段以外，还包含了时间字段。
 *
 * @author pengboran
 */
@Data
@MappedSuperclass
@EqualsAndHashCode(callSuper = false)
public class EntityUser extends EntityTime {

    @Column(nullable = false, columnDefinition = "varchar(255) default ''")
    private String createUser = "";

    @Column(nullable = false, columnDefinition = "varchar(255) default ''")
    private String updateUser = "";
}
