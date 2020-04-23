package com.lr.user_manager_system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@MapperScan("com.lr.user_manager_system.mapper")
@EnableCaching
@SpringBootApplication
public class UserManagerSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserManagerSystemApplication.class, args);
    }

}
