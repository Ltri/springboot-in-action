package com.ltri.elasticsearch.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ltri.elasticsearch.entity.Goods;
import com.ltri.elasticsearch.mapper.GoodsMapper;
import com.ltri.elasticsearch.service.GoodsService;
import org.springframework.stereotype.Service;

/**
 * @author ltri
 * @date 2020/5/25 3:38 下午
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
}
