package com.mengbai.yygh.msg.controller;

import com.mengbai.yygh.cmn.client.RedisRemoteService;
import com.mengbai.yygh.common.result.Result;
import com.mengbai.yygh.common.result.ResultCodeEnum;
import com.mengbai.yygh.common.util.CrowdUtil;
import com.mengbai.yygh.msg.config.ShortMessageProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author zcc
 * @version 2021/04/24/21:58
 * @description <注册功能：发送短信验证码>
 * @create 2021/04/24/21:58
 */
@Api("发送短信")
@RestController
@RequestMapping("/api/msm")
public class MsmApiController {
	@Autowired
	private ShortMessageProperties shortMessageProperties;

	@Autowired
	private RedisRemoteService redisRemoteService;

	private Logger logger = LoggerFactory.getLogger(MsmApiController.class);

	@ApiOperation("发送短信")
	@GetMapping("send/{phone}")
	public Result sendMessage(@PathVariable("phone") String phone) {
//        1.发送短信
		Result<String> sendMessageResultEntity =
				CrowdUtil.sendCodeByShortMessage(shortMessageProperties.getHost(),
						shortMessageProperties.getPath(),
						shortMessageProperties.getMethod(),
						phone,
						shortMessageProperties.getAppCode(),
						shortMessageProperties.getSkin());
//        2.判断短信是否发送成功

		if (200 == sendMessageResultEntity.getCode()) {
			logger.info(sendMessageResultEntity.getData() + " " + sendMessageResultEntity.getCode() + " " + sendMessageResultEntity.getMessage());
//            如果发送成功，则将验证码存入Redis
			String code = sendMessageResultEntity.getData();
			String key = ResultCodeEnum.REDIS_CODE_PREFIX_ + phone;
			Result<String> saveRedisResultEntity = redisRemoteService.setRedisKeyValueRemoteWithTimeOut(key, code, 5, TimeUnit.DAYS);
//            判断存入Redis是否成功
			if (200 == saveRedisResultEntity.getCode()) {
				return Result.ok();
			} else {
				return saveRedisResultEntity;
			}
		} else {
			return sendMessageResultEntity;


		}
	}

}
