package com.pbr.dao.mysql.entity;

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
public class BaseMysqlUserPojo extends BaseMysqlTimePojo {

    @Column(name = "create_user", nullable = false)
    private String createUser = "";

    @Column(name = "update_user", nullable = false)
    private String updateUser = "";
}
