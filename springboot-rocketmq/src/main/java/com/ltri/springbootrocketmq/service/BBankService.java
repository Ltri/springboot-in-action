package com.ltri.springbootrocketmq.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ltri.springbootrocketmq.entity.BBank;
import com.ltri.springbootrocketmq.mapper.BBankMapper;
import org.springframework.stereotype.Service;

/**
 * @author ltri
 * @date 2020/4/17 12:29 上午
 */
@Service
@DS("b_bank")
public class BBankService extends ServiceImpl<BBankMapper, BBank> {
}
