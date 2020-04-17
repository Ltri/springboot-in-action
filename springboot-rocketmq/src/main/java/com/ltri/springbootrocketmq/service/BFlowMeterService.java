package com.ltri.springbootrocketmq.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ltri.springbootrocketmq.entity.BFlowMeter;
import com.ltri.springbootrocketmq.mapper.BFlowMeterMapper;
import org.springframework.stereotype.Service;

/**
 * @author ltri
 * @date 2020/4/16 11:22 下午
 */
@Service
@DS("b_bank")
public class BFlowMeterService extends ServiceImpl<BFlowMeterMapper, BFlowMeter> {
}
