package com.ltri.springbootrocketmq.consumer;

import com.ltri.springbootrocketmq.entity.Goods;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author ltri
 * @date 2020/4/16 9:30 下午
 */
@Component
@RocketMQMessageListener(topic = "general-topic", consumerGroup = "goods-group")
public class GeneralConsumer implements RocketMQListener<Goods> {

    @Override
    public void onMessage(Goods message) {
        System.out.println(message);
    }
}
