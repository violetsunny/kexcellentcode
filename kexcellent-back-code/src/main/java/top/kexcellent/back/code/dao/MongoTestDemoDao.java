/**
 * LY.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package top.kexcellent.back.code.dao;


import top.kexcellent.back.code.model.MongoTestEntity;

/**
 * @author kanglele01
 * @version $Id: MongoTestDemoDao, v 0.1 2020/8/28 14:54 kanglele01 Exp $
 */
public interface MongoTestDemoDao {
    void saveDemo(MongoTestEntity demoEntity);

    void removeDemo(String id);

    void updateDemo(MongoTestEntity demoEntity);

    MongoTestEntity findDemoById(String id);
}
