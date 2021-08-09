package com.kang.kmall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.kang.kmall.mapper")
public class KmallApplication {

    public static void main(String[] args) {
        SpringApplication.run(KmallApplication.class, args);
    }

}
