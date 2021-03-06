package com.thumbing.pushdata.nodeserver.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thumbing.pushdata.common.handlers.IMessageHandler;
import com.thumbing.pushdata.common.message.ChatData;
import com.thumbing.pushdata.common.message.DefinedMessage;
import com.thumbing.pushdata.common.message.GroupData;
import com.thumbing.pushdata.nodeserver.channel.DataFlowChannelManager;
import com.thumbing.pushdata.nodeserver.channel.DeviceDataChannelManager;
import com.thumbing.pushdata.nodeserver.config.NodeServerConfig;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Stan Sai
 * @date 2020-08-11 21:23
 */
@Slf4j
@Data
public abstract class ChatDataHandler implements IMessageHandler<ChatData> {
    @Autowired
    DataFlowChannelManager dataFlowChannelManager;

    @Autowired
    NodeServerConfig nodeServerConfig;

    @Autowired
    DeviceDataChannelManager deviceChannelManager;

    @Override
    public boolean support(DefinedMessage message) {
        return message instanceof ChatData;
    }

    @Override
    public void call(ChannelHandlerContext ctx, ChatData message) throws JsonProcessingException {
        Channel channel = deviceChannelManager.getChannel(message.getToUser());
        if (channel != null) {
            try {
                channel.writeAndFlush(message.encode());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else if(!message.isLast()){
            Channel dataChannel = dataFlowChannelManager.getRandomChannel();
            dataChannel.writeAndFlush(message.encode());
        }
        log.info("node server send chat data");
    }
}
