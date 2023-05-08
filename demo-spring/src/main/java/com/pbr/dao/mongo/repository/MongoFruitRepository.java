package com.pbr.dao.mongo.repository;

import com.pbr.dao.mongo.entity.MongoFruit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoFruitRepository extends MongoRepository<MongoFruit, String> {

}