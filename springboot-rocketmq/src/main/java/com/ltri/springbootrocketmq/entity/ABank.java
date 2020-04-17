package com.ltri.springbootrocketmq.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author ltri
 * @date 2020/4/17 12:28 上午
 */
@Data
public class ABank {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long money;
    private String username;
}
