package com.mengbai.yygh.user.controller;

import com.mengbai.yygh.common.result.Result;
import com.mengbai.yygh.user.service.UserInfoService;
import com.mengbai.yygh.vo.user.LoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author zcc
 * @version 2021/04/24/18:02
 * @description <描述>
 * @create 2021/04/24/18:02
 */
@Api(tags = "用户登录注册")
@RestController
@RequestMapping("/api/user")
public class UserInfoController {

	@Resource
	private UserInfoService userInfoService;

	private Logger logger = LoggerFactory.getLogger(UserInfoController.class);

	@ApiOperation(value = "会员登录")
	@PostMapping("login")
	public Result login(@RequestBody LoginVo loginVo) {
		Map<String, Object> loginVoMap = userInfoService.login(loginVo);
		String name = (String) loginVoMap.get("name");
		String token = (String) loginVoMap.get("token");
		logger.info("name：" +name);
		logger.info("token：" +token);

		return Result.ok(loginVoMap);

	}
}
