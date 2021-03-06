package com.thumbing.shared.entity.sql;


import com.thumbing.shared.constants.EntityConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * @author Stan Sai
 * @date 2020-07-05
 */
@MappedSuperclass
@Getter
@Setter
@FieldNameConstants
public class SqlCreationEntity extends BaseSqlEntity {
    private static final long serialVersionUID = -305433542418558052L;

    @Column(name = EntityConstants.CREATE_TIME)
    private LocalDateTime createTime;
}
