package com.ltri.springbootrabbitmq.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Ltri
 * @date 2020/7/19 12:49 上午
 */
@Data
public class MessageStruct implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
}
