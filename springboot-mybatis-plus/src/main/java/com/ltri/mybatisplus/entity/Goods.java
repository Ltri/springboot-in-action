package com.ltri.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author ltri
 * @date 2020/4/16 12:13 下午
 */
@Data
public class Goods {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Long stock;
}
