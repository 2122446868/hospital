package com.mengbai.yygh.hosp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * ServiceHOspApplication
 * <功能详细描述>
 *
 * @author 赵长春
 * @version [版本号, 2021/4/6 13:36]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.mengbai")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.mengbai")
public class ServiceHospApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServiceHospApplication.class, args);
	}
}


