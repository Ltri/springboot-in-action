package com.ltri.springbootrabbitmq.controller;

import com.ltri.springbootrabbitmq.Constants.RabbitConstants;
import com.ltri.springbootrabbitmq.dto.MessageStruct;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ltri
 * @date 2020/7/19 12:47 上午
 */
@RestController
public class RabbitMQController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/test")
    public void test() {
        MessageStruct messageStruct = new MessageStruct();
        messageStruct.setId(11L);
        messageStruct.setName("aaa");

        rabbitTemplate.convertAndSend(RabbitConstants.DELAY_MODE_QUEUE, RabbitConstants.DELAY_QUEUE_SWITCH_USER,
                messageStruct,
                message -> {
                    message.getMessageProperties().setHeader("x-delay", 5 * 1000);
                    return message;
                });
    }

    @GetMapping("/test2")
    public void test2() {
        rabbitTemplate.convertAndSend(RabbitConstants.DIRECT_MODE_QUEUE_ONE, "direct message 测试中文");
    }
}
