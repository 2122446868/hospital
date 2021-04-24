package com.mengbai.yygh.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zcc
 * @version 2021/04/24/17:57
 * @description <描述>
 * @create 2021/04/24/17:57
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.mengbai")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.mengbai")
public class ServiceUserApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServiceUserApplication.class, args);
	}
}
