package com.ltri.jwt.service.impl;

import com.ltri.jwt.entity.Permission;
import com.ltri.jwt.mapper.PermissionMapper;
import com.ltri.jwt.service.IPermissionService;
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
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}
