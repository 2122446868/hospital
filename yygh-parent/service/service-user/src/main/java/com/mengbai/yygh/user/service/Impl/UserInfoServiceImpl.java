package com.mengbai.yygh.user.service.Impl;

import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mengbai.yygh.common.exception.YyghException;
import com.mengbai.yygh.common.help.JwtHelper;
import com.mengbai.yygh.common.result.ResultCodeEnum;
import com.mengbai.yygh.model.user.UserInfo;
import com.mengbai.yygh.user.mapper.UserInfoMapper;
import com.mengbai.yygh.user.service.UserInfoService;
import com.mengbai.yygh.vo.user.LoginVo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zcc
 * @version 2021/04/24/18:02
 * @description <描述>
 * @create 2021/04/24/18:02
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {


	/***
	 * 会员登录
	 * @param loginVo
	 * @return
	 */
	@Override
	public Map<String, Object> login(LoginVo loginVo) {
		// 判断手机号 和验证码不为空
		if (StringUtils.isEmpty(loginVo.getCode()) || StringUtils.isEmpty(loginVo.getCode())) {

			throw new YyghException(ResultCodeEnum.DATA_ERROR);
		}

		//查询验证码判断是否一致

		//根据手机号查询用户
		QueryWrapper<UserInfo> queryWapper = new QueryWrapper<>();
		queryWapper.eq("phone", loginVo.getPhone());
		UserInfo userInfo = baseMapper.selectOne(queryWapper);
		//判断是否第一次登录，如果是第一次登录给用户注册
		if (null == userInfo) {
			userInfo = new UserInfo();
			userInfo.setPhone(loginVo.getPhone());
			userInfo.setName(loginVo.getPhone());
			userInfo.setStatus(1);
			baseMapper.insert(userInfo);
		}
		//校验是否被禁用
		if (userInfo.getStatus() == 0) {
			throw new YyghException(ResultCodeEnum.LOGIN_DISABLED_ERROR);
		}
		//返回页面显示名称
		Map<String, Object> map = new HashMap<>();
		String name = userInfo.getName();
		if (null != name) {
			name = userInfo.getName();
		}
		if (null != name) {
			name = userInfo.getNickName();
		}
		// 创建token
		String token = JwtHelper.createToken(userInfo.getId(), name);
		map.put("name", name);
		map.put("token", token);
		return map;
	}
}
