package com.ltri.springbootrocketmq.transaction;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ltri.springbootrocketmq.dto.TransferDTO;
import com.ltri.springbootrocketmq.entity.ABank;
import com.ltri.springbootrocketmq.entity.AFlowMeter;
import com.ltri.springbootrocketmq.exception.BusinessException;
import com.ltri.springbootrocketmq.service.ABankService;
import com.ltri.springbootrocketmq.service.AFlowMeterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author ltri
 * @date 2020/4/16 11:34 下午
 */
@Service
@Slf4j
public class TransactionService {
    @Autowired
    private ABankService aBankService;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private AFlowMeterService AFlowMeterService;

    @Transactional(rollbackFor = Exception.class)
    public void transactionSend(TransferDTO transferDTO) {
        ABank aBank = aBankService.getOne(Wrappers.<ABank>lambdaQuery().eq(ABank::getUsername, transferDTO.getFormUser()));
        if (aBank.getMoney() < transferDTO.getMoney()) {
            throw new BusinessException("金额不足");
        }
        transferDTO.setFlowKey(Optional.ofNullable(transferDTO.getFlowKey()).orElse(IdWorker.getId()));
        //消息发送
        Message<TransferDTO> message = MessageBuilder.withPayload(transferDTO).build();
        log.info("1.发送消息{}", JSON.toJSONString(transferDTO));
        rocketMQTemplate.sendMessageInTransaction("transaction-topic", message, null);
    }

    @Transactional(rollbackFor = Exception.class)
    public void transactionCallBack(TransferDTO transferDTO) {
        log.info("4.回调消息开始处理{}", JSON.toJSONString(transferDTO));
        //消息幂等处理
        int count = AFlowMeterService.count(Wrappers.<AFlowMeter>lambdaQuery().eq(AFlowMeter::getFlowKey, transferDTO.getFlowKey()));
        if (count > 0) {
            log.info("重复消息{}", JSON.toJSON(transferDTO));
            return;
        }
        ABank aBank = aBankService.getOne(Wrappers.<ABank>lambdaQuery().eq(ABank::getUsername, transferDTO.getFormUser()));
        if (aBank.getMoney() < transferDTO.getMoney()) {
            throw new BusinessException("金额不足");
        }
        //流水表插入
        AFlowMeter AFlowMeter = new AFlowMeter();
        AFlowMeter.setType("goods");
        AFlowMeter.setFlowKey(transferDTO.getFlowKey());
        AFlowMeterService.save(AFlowMeter);
        log.info("5.流水表插入{}", JSON.toJSONString(AFlowMeter));

        aBank.setMoney(aBank.getMoney() - transferDTO.getMoney());
        aBankService.updateById(aBank);
        log.info("6.回调消息处理结束");
    }
}
