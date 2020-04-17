package com.ltri.jwt.service.impl;

import com.ltri.jwt.entity.UserRole;
import com.ltri.jwt.mapper.UserRoleMapper;
import com.ltri.jwt.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ltri
 * @since 2020-01-02
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
