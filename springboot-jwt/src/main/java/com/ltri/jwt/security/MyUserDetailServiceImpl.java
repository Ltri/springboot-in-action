package com.ltri.jwt.security;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ltri.jwt.entity.JwtUser;
import com.ltri.jwt.entity.User;
import com.ltri.jwt.entity.UserRole;
import com.ltri.jwt.service.IRoleService;
import com.ltri.jwt.service.IUserRoleService;
import com.ltri.jwt.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ltri
 */
@Component
public class MyUserDetailServiceImpl implements UserDetailsService {
    private static final String ROLE_PREFIX = "ROLE_";

    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IUserRoleService userRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username));
        if (user == null) {
            throw new UsernameNotFoundException("无此用户");
        }
        return create(user);
    }

    private UserDetails create(User user) {
        JwtUser jwtUser = new JwtUser();
        jwtUser.setUsername(user.getUsername());
        jwtUser.setPassword(user.getPassword());
        Set<Long> roleIds = userRoleService.list(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, user.getId())).stream().map(UserRole::getRoleId).collect(Collectors.toSet());
        Set<String> roles = (roleService.listByIds(roleIds)).stream().map(role -> ROLE_PREFIX + role.getName()).collect(Collectors.toSet());
        jwtUser.setRoles(roles);
        return jwtUser;
    }
}
