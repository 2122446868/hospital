package com.mengbai.yygh.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mengbai.yygh.model.user.UserInfo;
import com.mengbai.yygh.vo.user.LoginVo;

import java.util.Map;

/**
 * @author zcc
 * @version 2021/04/24/18:02
 * @description <描述>
 * @create 2021/04/24/18:02
 */
public interface UserInfoService extends IService<UserInfo> {
	/***
	 * 会员登录
	 * @param loginVo
	 * @return
	 */
	Map<String,Object> login(LoginVo loginVo);
}
