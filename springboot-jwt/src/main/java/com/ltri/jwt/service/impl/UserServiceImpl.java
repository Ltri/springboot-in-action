package com.ltri.jwt.service.impl;

import com.ltri.jwt.entity.User;
import com.ltri.jwt.mapper.UserMapper;
import com.ltri.jwt.service.IUserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
