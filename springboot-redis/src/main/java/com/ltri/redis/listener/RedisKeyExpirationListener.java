package com.ltri.redis.listener;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * @author Ltri
 * @date 2020/7/16 8:08 下午
 */

@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {
    public RedisKeyExpirationListener(RedisMessageListenerContainer container) {
        super(container);
    }

    /**
     * 针对redis数据失效事件，进行数据处理
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String key = message.toString();//生效的key
        if (key != null && key.startsWith("test")) {//从失效key中筛选代表订单失效的key
            //截取订单号，查询订单，如果是未支付状态则取消订单
            System.out.println("订单号为：" + key + "的订单超时未支付，取消订单");

        }
    }
}