package com.mengbai.yygh.msg.controller;

import com.baomidou.mybatisplus.extension.api.R;

import com.mengbai.yygh.common.result.Result;
import com.mengbai.yygh.common.util.CrowdUtil;
import com.mengbai.yygh.msg.config.ShortMessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author zcc
 * @version 2021/04/24/21:58
 * @description <注册功能：发送短信验证码>
 * @create 2021/04/24/21:58
 */
@RestController
@RequestMapping("/api/msm")
public class MsmApiController {

	@Autowired
	private ShortMessageProperties shortMessageProperties;

	@RequestMapping("send/{phone}")
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
		if ("SUCCESS".equals(sendMessageResultEntity.getData())) {
			logger.info(sendMessageResultEntity.getData() + " " + sendMessageResultEntity.getResult() + " " + sendMessageResultEntity.getMessage());
//            如果发送成功，则将验证码存入Redis
			String code = sendMessageResultEntity.getData();
			String key = CrowdConstant.REDIS_CODE_PREFIX_ + phoneNum;
			ResultEntity<String> saveRedisResultEntity = redisRemoteService.setRedisKeyValueRemoteWithTimeOut(key, code, 5, TimeUnit.DAYS);
//            判断存入Redis是否成功
			if (ResultEntity.SUCCESS.equals(saveRedisResultEntity.getResult())) {
				return ResultEntity.successWithoutData();
			} else {
				return saveRedisResultEntity;
			}
		} else {
			return sendMessageResultEntity;


		}
	}

}
