package com.thumbing.pushdata.common.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thumbing.pushdata.common.message.DefinedMessage;
import io.netty.channel.ChannelHandlerContext;


/**
 * @author Stan Sai
 * @date 2020-06-21
 */
public interface IMessageHandler<T> {
    /**
     * 根据各个handler 判断是不是各个handler对应处理的消息
     *
     * @param message 节点消息
     * @return 是否各个NodeMessage 子类的类型
     */
    boolean support(DefinedMessage message);


    /**
     * 各个消息处理句柄调用方法
     *
     * @param message 节点消息
     */
    void call(ChannelHandlerContext ctx, T message) throws JsonProcessingException;
}
