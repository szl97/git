package com.loserclub.pushdata.nodeserver.handlers.device;

import com.loserclub.pushdata.common.constants.AttributeEnum;
import com.loserclub.pushdata.common.constants.OperationEnum;
import com.loserclub.pushdata.common.message.ConnectSet;
import com.loserclub.pushdata.common.message.DefinedMessage;
import com.loserclub.pushdata.common.message.HandShake;
import com.loserclub.pushdata.nodeserver.channel.DeviceDataChannelManager;
import com.loserclub.pushdata.nodeserver.channel.SyncClientChannelManager;
import com.loserclub.pushdata.nodeserver.config.NodeServerConfig;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stan Sai
 * @date 2020-06-23
 */
@Slf4j
@Component
@Data
public class HandShakeHandler implements IDeviceDataHandler<HandShake> {

    @Autowired
    private DeviceDataChannelManager channelManager;

    @Autowired
    SyncClientChannelManager syncClientChannelManager;

    @Autowired
    NodeServerConfig nodeServerConfig;

    @Override
    public boolean support(DefinedMessage message) {
        return message instanceof HandShake;
    }

    @Override
    public void call(ChannelHandlerContext ctx, HandShake message) {
        Channel channel = ctx.channel();
        List<AttributeEnum> attributeEnums = new ArrayList<>();
        attributeEnums.add(AttributeEnum.CHANNEL_ATTR_DEVICE);
        attributeEnums.add(AttributeEnum.CHANNEL_ATTR_HANDSHAKE);
        channelManager.bindAttributes(message.getDeviceId(), channel, attributeEnums);
        log.debug("Device connect with node server,channel:{}", channel);
        List<Channel> channels = syncClientChannelManager.getAllChannels();
        List<Long> devices = new ArrayList<>();
        devices.add(message.getDeviceId());
        channels.forEach(
                c-> {
                    try {
                        c.writeAndFlush(ConnectSet.builder().name(nodeServerConfig.getName())
                                .operation(OperationEnum.ADD)
                                .deviceIds(devices)
                                .build()
                                .encode());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
        log.debug("node server send sync client request");
    }
}