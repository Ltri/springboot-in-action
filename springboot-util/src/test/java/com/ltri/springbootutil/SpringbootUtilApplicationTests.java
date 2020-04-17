package com.ltri.springbootutil;

import com.ltri.springbootutil.dto.GoodsDTO;
import com.ltri.springbootutil.util.valid.BeanValidator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootUtilApplicationTests {

    @Test
    void contextLoads() {
        GoodsDTO goodsDTO = new GoodsDTO();
        BeanValidator.validate(goodsDTO);
    }

}
