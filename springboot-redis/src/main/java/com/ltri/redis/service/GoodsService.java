package com.ltri.redis.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ltri.redis.entity.Goods;
import com.ltri.redis.mapper.GoodsMapper;
import org.springframework.stereotype.Service;

/**
 * @author ltri
 * @date 2020/3/31 11:53 PM
 */
@Service
public class GoodsService extends ServiceImpl<GoodsMapper, Goods> {

}
