package com.mengbai.yygh.user.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * HospConfig
 * <功能详细描述>
 *
 * @author 赵长春
 * @version [版本号, 2021/4/6 14:14]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Configuration
@MapperScan("com.mengbai.yygh.user.mapper")
public class UserConfig {

}
