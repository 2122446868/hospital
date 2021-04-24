package com.mengbai.yygh.msg.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zcc
 * @version 2021/04/24/22:11
 * @description <第三方短信接口信息，配置在配置文件当中>
 * @create 2021/04/24/22:11
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Component
@ConfigurationProperties(prefix = "short.message")
public class ShortMessageProperties {
	private String host;
	private String path;
	private String method;
	private String appCode;
	//    private String sign;
	private String skin;

}
