package com.pbr.dao.mongo.repository;

import com.pbr.dao.mongo.entity.KsBinSpaceUpdateRecord;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author niekang
 */
@Repository
public interface KsBinSpaceUpdateRecordRepository extends MongoRepository<KsBinSpaceUpdateRecord, ObjectId> {

    KsBinSpaceUpdateRecord findFirstByKsBinIdAndCreateTimeLessThanOrderByCreateTimeDesc(Long ksBinId, Date date);
}
