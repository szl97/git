package com.thumbing.shared.entity.mongo.content;

import com.thumbing.shared.constants.EntityConstants;
import com.thumbing.shared.entity.mongo.MongoFullAuditedEntity;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/18 12:49
 */
@Document(collection = "roast")
@Data
@SQLDelete(sql =  "update roast " + EntityConstants.NO_VERSION_DELETION)
@Where(clause = "is_delete=0")
//user_id, content, total_days, is_fishing, thumb_user_ids
public class Roast extends MongoFullAuditedEntity {
    /**
     * 用户id
     */
    private long userId;
    /**
     * 标题
     */
    private String title;
    /**
     * 可见天数
     */
    private int totalDays;
    /**
     * 点赞用户
     */
    private Set<Long> thumbUserIds;
    /**
     * 不可见后是否可以在一段时间后被打捞
     */
    private boolean fishing;
}
