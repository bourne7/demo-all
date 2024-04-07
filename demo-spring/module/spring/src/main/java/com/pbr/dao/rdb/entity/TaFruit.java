package com.pbr.dao.rdb.entity;

import com.pbr.dao.rdb.helper.EntityUser;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author pbr
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
        indexes = {
                @Index(name = "uk_code", columnList = "code", unique = true)
        }
)
@DynamicUpdate
public class TaFruit extends EntityUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(255) default '' comment 'Fruit name.'")
    private String code;

    private Integer size;

    @Version
    private long version;

}
