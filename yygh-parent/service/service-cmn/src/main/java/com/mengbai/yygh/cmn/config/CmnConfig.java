package com.mengbai.yygh.cmn.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * CmnConfig
 * <功能详细描述>
 *
 * @author 赵长春
 * @version [版本号, 2021/4/9 20:36]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Configuration
@MapperScan("com.mengbai.yygh.cmn.mapper")
public class CmnConfig {

    /***
     * 分页插件
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
