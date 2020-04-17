package com.ltri.springbootrocketmq.transaction;

import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ltri.springbootrocketmq.dto.TransferDTO;
import com.ltri.springbootrocketmq.entity.BBank;
import com.ltri.springbootrocketmq.entity.BFlowMeter;
import com.ltri.springbootrocketmq.service.BBankService;
import com.ltri.springbootrocketmq.service.BFlowMeterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ltri
 * @date 2020/4/16 11:42 下午
 */
@Component
@DS("b_bank")
@Slf4j
@RocketMQMessageListener(topic = "transaction-topic", consumerGroup = "rocketMQTemplate")
public class TransactionConsumer implements RocketMQListener<TransferDTO> {
    @Autowired
    private BBankService bBankService;
    @Autowired
    private BFlowMeterService bFlowMeterService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onMessage(TransferDTO transferDTO) {
        log.info("7.消费者消费消息开始{}", JSON.toJSONString(transferDTO));
        //消息幂等处理
        int count = bFlowMeterService.count(Wrappers.<BFlowMeter>lambdaQuery().eq(BFlowMeter::getFlowKey, transferDTO.getFlowKey()));
        if (count > 0) {
            log.info("重复消息{}", JSON.toJSON(transferDTO));
            return;
        }
        //int a = 1 / 0;
        BBank bBank = bBankService.getOne(Wrappers.<BBank>lambdaQuery().eq(BBank::getUsername, transferDTO.getToUser()));
        bBank.setMoney(bBank.getMoney() + transferDTO.getMoney());
        bBankService.updateById(bBank);
        //流水表插入
        BFlowMeter bFlowMeter = new BFlowMeter();
        bFlowMeter.setType("goods");
        bFlowMeter.setFlowKey(transferDTO.getFlowKey());
        bFlowMeterService.save(bFlowMeter);
        log.info("8.消费者消费消息结束");
    }
}
