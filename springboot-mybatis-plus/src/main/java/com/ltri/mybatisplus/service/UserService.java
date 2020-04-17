package com.ltri.mybatisplus.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ltri.mybatisplus.entity.User;
import com.ltri.mybatisplus.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @author ltri
 * @date 2020/4/16 5:44 下午
 */
@DS("example")
@Service
public class UserService extends ServiceImpl<UserMapper, User> {
}
