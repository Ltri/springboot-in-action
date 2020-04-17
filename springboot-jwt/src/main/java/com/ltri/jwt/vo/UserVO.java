package com.ltri.jwt.vo;

import com.ltri.jwt.entity.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserVO {
    private Long id;

    private String username;

    //private String password;

    private Set<Role> roles;

    private Set<String> permissions;
}
