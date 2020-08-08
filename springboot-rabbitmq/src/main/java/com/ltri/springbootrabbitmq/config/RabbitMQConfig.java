package com.ltri.springbootrabbitmq.config;

import com.google.common.collect.Maps;
import com.ltri.springbootrabbitmq.Constants.RabbitConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;


/**
 * @author Ltri
 * @date 2020/7/17 2:26 下午
 */
@Configuration
@Slf4j
public class RabbitMQConfig {

    /**
     * 直接模式队列1
     */
    @Bean
    public Queue directOneQueue() {
        return new Queue(RabbitConstants.DIRECT_MODE_QUEUE_ONE);
    }

    /**
     * 30分钟经纪人无联系用户延迟队列
     */
    @Bean
    public Queue delayQueueSwitchUser() {
        return new Queue(RabbitConstants.DELAY_QUEUE_SWITCH_USER);
    }

    /**
     * 延迟队列交换器, x-delayed-type 和 x-delayed-message 固定
     */
    @Bean
    public CustomExchange delayExchange() {
        Map<String, Object> args = Maps.newHashMap();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(RabbitConstants.DELAY_MODE_QUEUE, "x-delayed-message", true, false, args);
    }

    /**
     * 延迟队列绑定自定义交换器
     *
     * @param delayQueueSwitchUser 队列
     * @param delayExchange        延迟交换器
     */
    @Bean
    public Binding delayBinding(Queue delayQueueSwitchUser, CustomExchange delayExchange) {
        return BindingBuilder.bind(delayQueueSwitchUser).to(delayExchange).with(RabbitConstants.DELAY_QUEUE_SWITCH_USER).noargs();
    }
}
