package com.ltri.jwt.biz;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ltri.jwt.dto.UserDTO;
import com.ltri.jwt.entity.Permission;
import com.ltri.jwt.entity.RolePermission;
import com.ltri.jwt.entity.User;
import com.ltri.jwt.entity.UserRole;
import com.ltri.jwt.exception.BusinessException;
import com.ltri.jwt.service.*;
import com.ltri.jwt.util.JwtTokenUtils;
import com.ltri.jwt.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserBiz {
    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private IPermissionService permissionService;
    @Autowired
    private IRolePermissionService rolePermissionService;
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 查询所有用户
     */
    public List<UserVO> listUserDTO() {
        List<User> userList = userService.list();
        List<UserVO> userVOList = new ArrayList<>();
        userList.forEach(user -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            //查询当前用户所有角色
            Set<Long> roleIds = userRoleService.list(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, user.getId())).stream().map(UserRole::getRoleId).collect(Collectors.toSet());
            if (!roleIds.isEmpty()) {
                userVO.setRoles(new HashSet<>(roleService.listByIds(roleIds)));
                //查询并合并所有角色下的权限
                Set<Long> permissionIds = rolePermissionService.list(Wrappers.<RolePermission>lambdaQuery().in(RolePermission::getRoleId, roleIds)).stream().map(RolePermission::getPermissionId).collect(Collectors.toSet());
                userVO.setPermissions(permissionIds.isEmpty() ? null : permissionService.listByIds(permissionIds).stream().map(Permission::getAction).collect(Collectors.toSet()));
            }
            userVOList.add(userVO);
        });
        return userVOList;
    }

    /**
     * 查询单个用户
     */
    public UserVO getUserDTO(Long id) {
        User user = userService.getById(id);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        //查询当前用户所有角色
        Set<Long> roleIds = userRoleService.list(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, user.getId())).stream().map(UserRole::getRoleId).collect(Collectors.toSet());
        if (!roleIds.isEmpty()) {
            userVO.setRoles(new HashSet<>(roleService.listByIds(roleIds)));
            //查询并合并所有角色下的权限
            Set<Long> permissionIds = rolePermissionService.list(Wrappers.<RolePermission>lambdaQuery().in(RolePermission::getRoleId, roleIds)).stream().map(RolePermission::getPermissionId).collect(Collectors.toSet());
            userVO.setPermissions(permissionIds.isEmpty() ? null : permissionService.listByIds(permissionIds).stream().map(Permission::getAction).collect(Collectors.toSet()));
        }
        return userVO;
    }

    /**
     * 添加用户
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(UserDTO userDTO) {
        int count = userService.count(Wrappers.<User>lambdaQuery().eq(User::getUsername, userDTO.getUsername()));
        if (count > 0) {
            throw new BusinessException("用户已存在");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = encoder.encode(userDTO.getPassword());
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(password);
        //用户插入
        userService.save(user);

        List<UserRole> userRoles = new ArrayList<>();
        userDTO.getRoleIds().forEach(roleId -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getId());
            userRole.setRoleId(roleId);
            userRoles.add(userRole);
        });
        //用户对应角色插入
        userRoleService.saveBatch(userRoles);
    }

    /**
     * @description: 登录
     * @param: username 用户名
     * @param: password 密码
     */
    public String login(String username, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return JwtTokenUtils.generateToken(username);
    }
}
