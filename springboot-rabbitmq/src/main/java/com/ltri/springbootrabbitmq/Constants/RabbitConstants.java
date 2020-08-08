package com.ltri.springbootrabbitmq.Constants;

/**
 * @author Ltri
 * @date 2020/7/17 2:34 下午
 */
public class RabbitConstants {

    /**
     * 直接模式1
     */
    public static final String DIRECT_MODE_QUEUE_ONE = "queue.direct.1";

    /**
     * 30分钟切换经纪人队列
     */
    public static final String DELAY_QUEUE_SWITCH_USER = "MQ_DELAY_SWITCH_USER";

    /**
     * 延迟队列交换器
     */
    public static final String DELAY_MODE_QUEUE = "EX_SWITCH_USER";

}
