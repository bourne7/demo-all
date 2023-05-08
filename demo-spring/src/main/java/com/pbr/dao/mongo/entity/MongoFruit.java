package com.pbr.dao.mongo.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@Document("MongoDemo")
@NoArgsConstructor
public class MongoFruit {

    @Id
    private String id;
    @Field(value = "name")
    private String name;

    public MongoFruit(String name) {
        this.name = name;
    }
}
