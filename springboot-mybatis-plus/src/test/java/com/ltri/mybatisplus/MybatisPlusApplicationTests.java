package com.ltri.mybatisplus;

import com.ltri.mybatisplus.service.GoodsService;
import com.ltri.mybatisplus.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MybatisPlusApplicationTests {
    @Autowired
    UserService userService;
    @Autowired
    GoodsService goodsService;

    @Test
    void test4() {
        System.out.println(userService.list());
        System.out.println(goodsService.list());
    }

    @Test
    void test3() {
    }
}
