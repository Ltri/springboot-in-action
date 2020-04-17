package com.ltri.springbootrocketmq.consumer;

import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * @author ltri
 * @date 2020/4/2 9:55 PM
 */
@Service
@RocketMQMessageListener(topic = "sync-order-topic", consumerGroup = "sync-order-consumer",consumeMode = ConsumeMode.ORDERLY,consumeThreadMax = 3)
public class SyncOrderConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        System.out.println(message);
    }
}
