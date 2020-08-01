package com.thumbing.pushdata.common.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * @author Stan Sai
 * @date 2020-06-21
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatData extends NodeMessage<ChatData> {

    private Long fromUser;

    private List<Long> toUsers;

    private List<Long> deviceIds;

    private String name;

    private String data;

    @Override
    protected DefinedMessage.Type type() {
        return DefinedMessage.Type.CD;
    }

    @Override
    protected ChatData getThis() throws Exception {
        return this;
    }
}