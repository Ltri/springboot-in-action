package com.ltri.springbootutil.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author ltri
 * @date 2020/4/16 6:00 下午
 */
@Data
public class GoodsDTO {
    @NotNull(message = "id不能为空")
    private Long id;
    @NotBlank(message = "名称不能为空")
    private String name;
    @NotNull(message = "库存不能为空")
    private Long stock;
}
