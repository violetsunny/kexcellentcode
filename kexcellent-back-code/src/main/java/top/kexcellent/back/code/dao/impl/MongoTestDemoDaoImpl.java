/**
 * LY.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package top.kexcellent.back.code.dao.impl;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import top.kexcellent.back.code.dao.MongoTestDemoDao;
import top.kexcellent.back.code.model.MongoTestEntity;

import javax.annotation.Resource;

/**
 * @author kanglele01
 * @version $Id: MongoTestDemoDaoImpl, v 0.1 2020/8/28 14:56 kanglele01 Exp $
 */
@Service
public class MongoTestDemoDaoImpl implements MongoTestDemoDao {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public void saveDemo(MongoTestEntity demoEntity) {
        mongoTemplate.save(demoEntity);
    }

    @Override
    public void removeDemo(String id) {
        MongoTestEntity entity = new MongoTestEntity();
        entity.setId(id);
        mongoTemplate.remove(entity);
    }

    @Override
    public void updateDemo(MongoTestEntity demoEntity) {
        Query query = new Query(Criteria.where("id").is(demoEntity.getId()));

        Update update = new Update();
        update.set("title", demoEntity.getTitle());
        update.set("description", demoEntity.getDescription());
        update.set("by", demoEntity.getBy());
        update.set("url", demoEntity.getUrl());

        mongoTemplate.updateFirst(query, update, MongoTestEntity.class);
    }

    @Override
    public MongoTestEntity findDemoById(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        MongoTestEntity demoEntity = mongoTemplate.findOne(query, MongoTestEntity.class);
        return demoEntity;
    }
}
