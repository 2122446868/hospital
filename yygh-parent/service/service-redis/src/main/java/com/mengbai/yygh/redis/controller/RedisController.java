package com.mengbai.yygh.redis.controller;

import com.mengbai.yygh.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * RedisController
 * <redis 工具接口>
 *
 * @author mb
 * @version [版本号, 2021/4/25/10:28]
 */
@RestController
public class RedisController {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	/***
	 * 设置Redis key-value 没有超时设置
	 * @param key
	 * @param value
	 * @return
	 */
	@RequestMapping("/set/redis/key/value/remote")
	Result<String> setRedisKeyValueRemote(@RequestParam("key") String key, @RequestParam("value") String value) {
		try {
			ValueOperations<String, String> string = stringRedisTemplate.opsForValue();
			string.set(key, value);
			return Result.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return Result.fail(e.getMessage());
		}
	}

	/***
	 * 设置Redis key-value 可以设置超时
	 * @param key
	 * @param value
	 * @param timeout   超时时间
	 * @param timeUnit  时间单位
	 * @return
	 */
	@RequestMapping("/set/redis/key/value/remote/with/timeout")
	Result<String> setRedisKeyValueRemoteWithTimeOut(@RequestParam("key") String key,
	                                                 @RequestParam("value") String value,
	                                                 @RequestParam("timeout") long timeout,
	                                                 @RequestParam("timeUnit") TimeUnit timeUnit) {
		try {
			ValueOperations<String, String> string = stringRedisTemplate.opsForValue();
			string.set(key, value, timeout, timeUnit);
			return Result.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return Result.fail(e.getMessage());
		}
	}


	/***
	 * 获取Redis value
	 * @param key
	 * @return
	 */
	@RequestMapping("/get/redis/string/value/by/key")
	Result<String> getRedisStringValueByKeyRemote(@RequestParam("key") String key) {
		try {
			ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();

			String data = operations.get(key);

			return Result.ok(data);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.fail(e.getMessage());
		}
	}

	/***
	 * 删除Redis key
	 * @param key
	 * @return
	 */
	@RequestMapping("/remove/redis/key/remote")
	Result<String> removeRedisKeyRemote(@RequestParam("key") String key) {
		try {
			stringRedisTemplate.delete(key);

			return Result.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return Result.fail(e.getMessage());
		}
	}
}
