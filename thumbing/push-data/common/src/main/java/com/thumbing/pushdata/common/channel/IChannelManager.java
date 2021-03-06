package com.thumbing.pushdata.common.channel;

import com.thumbing.pushdata.common.constants.AttributeEnum;
import io.netty.channel.Channel;

import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author Stan Sai
 * @date 2020-06-21
 */
public interface IChannelManager<T> {

    void bindAttributes(T id, Channel channel, List<AttributeEnum> attributeKeys);

    T getNodeOrDeviceId(Channel channel);

    Channel getChannel(T id);

    void removeChannel(Channel channel);

    void removeChannel(T id);

    List<Channel> getAll();
}
