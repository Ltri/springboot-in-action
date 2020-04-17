package com.ltri.springbootrocketmq.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ltri
 * @date 2020/4/16 11:09 下午
 */
@Data
public class BFlowMeter {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String type;
    private Long flowKey;
    private LocalDateTime createTime;
}
