package com.ltri.springbootrocketmq.controller;

import com.ltri.springbootrocketmq.dto.TransferDTO;
import com.ltri.springbootrocketmq.entity.Goods;
import com.ltri.springbootrocketmq.transaction.TransactionService;
import io.swagger.annotations.ApiOperation;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author ltri
 * @date 2020/4/7 11:06 PM
 */
@RestController
public class RocketMQController {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/generalSend")
    @ApiOperation("普通发送")
    public void generalSend(@RequestBody Goods goods) {
        rocketMQTemplate.convertAndSend("general-topic:generalTag", goods);
    }

    @GetMapping("/send")
    @ApiOperation("普通发送")
    public void send(@RequestBody Goods goods) {
        Message<Goods> message = MessageBuilder.withPayload(goods).setHeader(MessageConst.PROPERTY_KEYS, "generalKey").build();
        rocketMQTemplate.send("general-topic:generalTag", message);
    }

    @GetMapping("/syncSend")
    @ApiOperation("同步发送")
    public void sendSync() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < 200; i++) {
            String msg = "syncSendMsg NO:" + i;
            rocketMQTemplate.syncSend("sync-topic", msg);
        }
        stopWatch.stop();
        System.out.println("耗费时间 " + stopWatch.getLastTaskTimeMillis());
    }

    @GetMapping("/asyncSend")
    @ApiOperation("异步发送")
    public void asyncSend() {
        long time1 = System.currentTimeMillis();
        for (int i = 0; i < 200; i++) {
            String msg = "asyncSendMsg NO:" + i;
            rocketMQTemplate.asyncSend("async-topic", msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("asyncSendMsg success");
                }

                @Override
                public void onException(Throwable throwable) {
                    System.out.println(throwable.getMessage());
                }
            });
        }
        long time2 = System.currentTimeMillis();
        System.out.println("耗费时间 " + (time2 - time1) + "ms");
    }

    @GetMapping("/syncSendOrderly")
    @ApiOperation("同步顺序发送")
    public void generalSend() {
        for (int i = 0; i < 10; i++) {
            String msg = "syncSendOrderlyMsg NO:" + i;
            rocketMQTemplate.syncSendOrderly("sync-order-topic", msg, "test");
        }
    }

    @GetMapping("/transactionSend")
    @ApiOperation("事务消息发送")
    public void generalSend(@RequestBody TransferDTO transferDTO) {
        transactionService.transactionSend(transferDTO);
    }

    @GetMapping("/delaySend")
    @ApiOperation("延时消息发送")
    public void asyncSendDelay() {
        for (int i = 0; i < 10; i++) {
            String msg = "delay-msg NO:" + i + LocalDateTime.now().toString();
            Message<String> message = MessageBuilder.withPayload(msg).build();
            rocketMQTemplate.asyncSend("delay-topic", message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("success");
                }

                @Override
                public void onException(Throwable throwable) {
                    System.out.println(throwable.getMessage());
                }
            }, 1000, 2);
        }
    }
}
