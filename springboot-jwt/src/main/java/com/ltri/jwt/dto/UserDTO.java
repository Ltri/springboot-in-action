package com.ltri.jwt.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

/**
 * @author ltri
 * @date 2020/4/16 4:37 下午
 */
@Data
public class UserDTO {
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
    @NotEmpty
    private Set<Long> roleIds;
}
