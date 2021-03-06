package com.psi.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

//排除扫描数据源
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableScheduling
public class JobApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobApplication.class, args);
        System.out.println("定时器启动......");
    }

}
