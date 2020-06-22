package com.loserclub.pushdata.datacenter.messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class Pong extends NodeMessageResp<Pong> {
    @Override
    protected Type type() {
        return Type.PO;
    }

    @Override
    protected Pong getThis() throws Exception {
        return this;
    }
}