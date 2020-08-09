package com.thumbing.usermanagement.dto.input;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thumbing.shared.utils.serializer.LongToStringSerializer;
import com.thumbing.shared.utils.serializer.StringToLongDeserializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/7 11:58
 */
@Data
public class BlackListAddInput implements Serializable {
    @ApiModelProperty(value = "被添加方用户Id")
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    private Long targetUserId;
}