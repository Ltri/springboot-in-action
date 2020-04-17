package com.ltri.excel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ltri.excel.mapper")
public class SpringbootExcelApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootExcelApplication.class, args);
    }
}
