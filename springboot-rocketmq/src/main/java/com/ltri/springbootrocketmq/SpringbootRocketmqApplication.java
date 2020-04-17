package com.ltri.springbootrocketmq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ltri.springbootrocketmq.mapper")
public class SpringbootRocketmqApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootRocketmqApplication.class, args);
    }

}
