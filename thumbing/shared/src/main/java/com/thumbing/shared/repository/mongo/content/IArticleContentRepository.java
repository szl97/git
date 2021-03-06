package com.thumbing.shared.repository.mongo.content;

import com.thumbing.shared.entity.mongo.content.ArticleContent;
import com.thumbing.shared.repository.mongo.IBaseMongoRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/18 17:38
 */
public interface IArticleContentRepository extends IBaseMongoRepository<ArticleContent> {
    Optional<ArticleContent> findByArticleId(String id);
}
