package com.mengbai.yygh.msg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zcc
 * @version 2021/04/24/21:56
 * @description <描述>
 * @create 2021/04/24/21:56
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//取消数据源自动配置
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.mengbai")
@EnableFeignClients(basePackages = "com.mengbai")
public class ServiceMsmApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServiceMsmApplication.class, args);
	}
}
