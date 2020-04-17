package com.ltri.springbootrocketmq.consumer;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author ltri
 * @date 2020/4/17 11:35 上午
 */
@Service
@RocketMQMessageListener(topic = "delay-topic", consumerGroup = "delay-consumer")
public class DelayConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        System.out.println(LocalDateTime.now() + ":" + message);
    }
}
