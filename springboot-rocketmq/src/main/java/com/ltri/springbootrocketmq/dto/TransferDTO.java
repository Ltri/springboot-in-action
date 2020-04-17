package com.ltri.springbootrocketmq.dto;

import lombok.Data;

/**
 * @author ltri
 * @date 2020/4/16 11:20 下午
 */
@Data
public class TransferDTO {
    private String formUser;
    private String toUser;
    private Long money;
    private Long flowKey;
}
