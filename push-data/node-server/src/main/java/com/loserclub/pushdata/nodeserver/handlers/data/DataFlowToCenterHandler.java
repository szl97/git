package com.loserclub.pushdata.nodeserver.handlers.data;

import com.loserclub.pushdata.common.message.DefinedMessage;
import com.loserclub.pushdata.nodeserver.handlers.PushDataHandler;
import com.loserclub.pushdata.nodeserver.messages.PushData;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 此处应该是成功确认操作，以及失败处理操作，以后补上，需要redis和数据库保存消息的状态
 */

@Slf4j
@Component
@Data
public class DataFlowToCenterHandler implements IDeviceDataHandler<PushData> {

    @Autowired
    private PushDataHandler pushDataHandler;

    @Override
    public boolean support(DefinedMessage<PushData> message) {
        return pushDataHandler.support(message);
    }

    @Override
    public void call(ChannelHandlerContext ctx, PushData message) throws Exception {
        pushDataHandler.call(ctx,message);
    }
}
