package com.loserclub.pushdata.nodeserver.handlers.sync;

import com.loserclub.pushdata.common.channel.IChannelManager;
import com.loserclub.pushdata.common.constants.AttributeEnum;
import com.loserclub.pushdata.common.message.DefinedMessage;
import com.loserclub.pushdata.nodeserver.channel.SyncClientChannelManager;
import com.loserclub.pushdata.nodeserver.handlers.INodeToCenterHandler;
import com.loserclub.pushdata.nodeserver.messages.Confirm;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * 没用的考虑删除
 */

/**
 * @author Stan Sai
 * @date 2020-06-23
 */
@Slf4j
@Component
@Data
public class SyncClientConfirmHandler  {

    @Autowired
    SyncClientChannelManager channelManager;

    //@Override
    public boolean support(DefinedMessage message) {
        return message instanceof Confirm;
    }

    //@Override
    public void call(ChannelHandlerContext ctx, Confirm message) {
        Channel channel = ctx.channel();
        List<AttributeEnum> attributeEnums = new ArrayList<>();
        attributeEnums.add(AttributeEnum.CHANNEL_ATTR_DATACENTER);
        channelManager.bindAttributes(message.getName(), channel, attributeEnums);
        log.debug("Data center receive confirm,channel:{}", channel);
    }
}