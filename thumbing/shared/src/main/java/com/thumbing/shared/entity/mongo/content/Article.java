package com.thumbing.shared.entity.mongo.content;

import com.thumbing.shared.constants.EntityConstants;
import com.thumbing.shared.entity.mongo.MongoFullAuditedEntity;
import com.thumbing.shared.entity.mongo.content.inner.InnerComment;
import com.thumbing.shared.entity.mongo.common.NickUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/18 12:18
 */
@Document(collection = "article")
@Data
@FieldNameConstants
//user_id, title, tags_ids, content, thumb_user_ids, innerComments,next_nick_name, graph_ids, browse_user_ids
public class Article extends MongoFullAuditedEntity {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 标题
     */
    private String title;
    /**
     * 标签
     */
    private Set<Long> tagIds;
    /**
     * 内容
     */
    private String content;
    /**
     * 点赞用户
     */
    private Set<Long> thumbUserIds;
    /**
     * 文章下的评论
     */
    private List<InnerComment> innerComments;
    /**
     * 参与评论的所有用户
     */
    private Set<NickUser> nickUsers;
    /**
     * 下一个评论用户显示的昵称
     */
    private String nextNickName;
    /**
     * 文章中的图片在OSS中的标识
     */
    private List<String> graphIds;
    /**
     * 浏览过的用户id
     */
    private Set<Long> browseUserIds;

}
