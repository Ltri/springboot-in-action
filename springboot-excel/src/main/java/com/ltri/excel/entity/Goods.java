package com.ltri.excel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
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
    @ExcelProperty("id")
    private Long id;
    @ExcelProperty("名称")
    private String name;
    @ExcelProperty("库存")
    private Long stock;
}
