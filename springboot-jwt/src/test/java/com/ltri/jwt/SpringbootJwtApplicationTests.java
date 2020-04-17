package com.ltri.jwt;
import com.google.common.collect.Sets;

import com.ltri.jwt.biz.UserBiz;
import com.ltri.jwt.dto.UserDTO;
import com.ltri.jwt.mapper.UserMapper;
import com.ltri.jwt.util.JwtTokenUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.HashSet;

@SpringBootTest
class SpringbootJwtApplicationTests {
    @Autowired
    UserMapper userMapper;
    @Autowired
    private UserBiz userBiz;
    @Test
    void contextLoads() {
        String aaa = JwtTokenUtils.generateToken("aaa");
        System.out.println("aaa = " + aaa);
        System.out.println(JwtTokenUtils.getUsername(aaa));
        System.out.println(JwtTokenUtils.getClaimsFromToken(aaa));
    }

    @Test
    void test1() {
        System.out.println(JwtTokenUtils.getClaimsFromToken("eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJsdHJpIiwic3ViIjoiYWFhIiwiZXhwIjoxNTc3OTUzMjcxLCJpYXQiOjE1Nzc5NDk2NzF9.MsK_343HHIDbbBBvFzwJqvDn7XbAy5LdsiG4eyzi8F0pge7BIoWnwBQBgL1yH1jpi2-Y5GqutDhA59jLLeYj3g"));
    }

    @Test
    void test3() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("user");
        userDTO.setPassword("pwd");
        HashSet<Long> set = Sets.newHashSet();
        set.add(1L);
        set.add(2L);
        userDTO.setRoleIds(set);
        userBiz.saveUser(userDTO);
    }

}
