package com.ltri.jwt.service.impl;

import com.ltri.jwt.entity.Role;
import com.ltri.jwt.mapper.RoleMapper;
import com.ltri.jwt.service.IRoleService;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
