package com.ltri.springbootrocketmq.transaction;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ltri.springbootrocketmq.dto.TransferDTO;
import com.ltri.springbootrocketmq.entity.AFlowMeter;
import com.ltri.springbootrocketmq.service.AFlowMeterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @author ltri
 * @date 2020/4/16 9:30 下午
 */
@Component
@RocketMQTransactionListener
@Slf4j
public class TransactionListener implements RocketMQLocalTransactionListener {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AFlowMeterService AFlowMeterService;

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object arg) {
        try {
            String msg = new String((byte[]) message.getPayload());
            log.info("2.解析到消息{}", msg);
            TransferDTO transferDTO = JSON.parseObject(msg, TransferDTO.class);
            log.info("3.事务回调{}", transferDTO);
            transactionService.transactionCallBack(transferDTO);
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    /**
     * 事务状态回查
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        log.info("事务状态回查");
        TransferDTO transferDTO = (TransferDTO) msg.getPayload();
        int count = AFlowMeterService.count(Wrappers.<AFlowMeter>lambdaQuery().eq(AFlowMeter::getFlowKey, transferDTO.getFlowKey()));
        if (count > 0) {
            return RocketMQLocalTransactionState.COMMIT;
        } else {
            return RocketMQLocalTransactionState.UNKNOWN;
        }
    }
}
