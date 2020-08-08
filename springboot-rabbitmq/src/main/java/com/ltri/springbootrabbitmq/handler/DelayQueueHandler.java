package com.ltri.springbootrabbitmq.handler;

import com.ltri.springbootrabbitmq.Constants.RabbitConstants;
import com.ltri.springbootrabbitmq.dto.MessageStruct;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Ltri
 * @date 2020/7/17 2:38 下午
 */
@Slf4j
@RabbitListener(queues = RabbitConstants.DELAY_QUEUE_SWITCH_USER)
@Component
public class DelayQueueHandler {

    @RabbitHandler
    public void directHandlerManualAck(MessageStruct messageStruct, Message message, Channel channel){
        //  如果手动ACK,消息会被监听消费,但是消息在队列中依旧存在,如果 未配置 acknowledge-mode 默认是会在消费完毕后自动ACK掉
        final long deliveryTag = message.getMessageProperties().getDeliveryTag();
        System.out.println(messageStruct);
        try {
            //int a = 1/0;
            log.info("延迟队列，手动ACK，接收消息：{}", message);
            // 通知 MQ 消息已被成功消费,可以ACK了
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            e.printStackTrace();
            // 处理失败,重新压入MQ
            try {
                channel.basicRecover();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
