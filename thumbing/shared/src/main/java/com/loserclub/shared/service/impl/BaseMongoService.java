package com.loserclub.shared.service.impl;


import com.loserclub.shared.entity.mongo.BaseMongoEntity;
import com.loserclub.shared.repository.mongo.IBaseMongoRepository;
import com.loserclub.shared.service.mongo.IBaseMongoService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Stan Sai
 * @date 2020-07-05
 */
public class BaseMongoService<T extends BaseMongoEntity, K extends IBaseMongoRepository<T>> implements IBaseMongoService<T, K> {
    @Autowired
    protected K mRepository;
}