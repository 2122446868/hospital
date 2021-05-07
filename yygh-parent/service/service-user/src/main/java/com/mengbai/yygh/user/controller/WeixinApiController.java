package com.mengbai.yygh.user.controller;

import com.mengbai.yygh.cmn.client.RedisRemoteService;
import com.mengbai.yygh.common.result.Result;
import com.mengbai.yygh.user.config.ConstantPropertiesUtil;
import com.mengbai.yygh.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * WeixinApiController
 * <功能描述>
 *
 * @author mb
 * @version [版本号, 2021/4/25/17:11]
 */
@Controller
@RequestMapping("/api/ucenter/wx")
public class WeixinApiController {
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private RedisRemoteService redisRemoteService;

	/**
	 * 获取微信登录参数
	 */
	@GetMapping("getLoginParam")
	@ResponseBody
	public Result genQrConnect(HttpSession session) throws UnsupportedEncodingException {
		String redirectUri = URLEncoder.encode(ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL, "UTF-8");
		Map<String, Object> map = new HashMap<>();
		map.put("appid", ConstantPropertiesUtil.WX_OPEN_APP_ID);
		map.put("redirectUri", redirectUri);
		map.put("scope", "snsapi_login");
		map.put("state", System.currentTimeMillis() + "");//System.currentTimeMillis()+""
		return Result.ok(map);
	}
}