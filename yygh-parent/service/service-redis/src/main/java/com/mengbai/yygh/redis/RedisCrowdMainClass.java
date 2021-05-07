package com.mengbai.yygh.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * CrowdMainClass
 * <功能详细描述>
 *
 * @author 赵长春
 * @version [版本号, 2021/2/28 18:59]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//取消数据源自动配置
@EnableDiscoveryClient
public class RedisCrowdMainClass {
    public static void main(String[] args) {
        SpringApplication.run(RedisCrowdMainClass.class, args);

    }
}
