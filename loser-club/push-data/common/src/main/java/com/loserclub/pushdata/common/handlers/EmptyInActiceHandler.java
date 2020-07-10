package com.loserclub.pushdata.common.handlers;

import com.loserclub.pushdata.common.message.DefinedMessage;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

/**
 * @author Stan Sai
 * @date 2020-07-10
 */
@Component
public class EmptyInActiceHandler implements IInActiveHandler {
    @Override
    public boolean support(DefinedMessage message) {
        return true;
    }

    @Override
    public void call(ChannelHandlerContext ctx, Object message) throws Exception {

    }
}
