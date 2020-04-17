package com.ltri.mybatisplus.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ltri.mybatisplus.entity.Goods;
import com.ltri.mybatisplus.mapper.GoodsMapper;
import org.springframework.stereotype.Service;

@Service
public class GoodsService extends ServiceImpl<GoodsMapper, Goods> {

}
