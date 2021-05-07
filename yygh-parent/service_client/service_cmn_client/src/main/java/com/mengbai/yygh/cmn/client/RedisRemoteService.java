package com.mengbai.yygh.cmn.client;

import com.mengbai.yygh.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.TimeUnit;

/**
 * RedisRemoteService
 * <功能描述>
 *
 * @author mb
 * @version [版本号, 2021/4/25/10:33]
 */
@Repository
@FeignClient("service-redis")
public interface RedisRemoteService {
	/***
	 * 设置Redis key-value 没有超时设置
	 * @param key
	 * @param value
	 * @return
	 */
	@RequestMapping("/set/redis/key/value/remote")
	Result<String> setRedisKeyValueRemote(@RequestParam("key") String key, @RequestParam("value") String value);

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
	                                                 @RequestParam("timeUnit") TimeUnit timeUnit);

	/***
	 * 获取Redis value
	 * @param key
	 * @return
	 */
	@RequestMapping("/get/redis/string/value/by/key")
	Result<String> getRedisStringValueByKeyRemote(@RequestParam("key") String key);

	/***
	 * 删除Redis key
	 * @param key
	 * @return
	 */
	@RequestMapping("/remove/redis/key/remote")
	Result<String> removeRedisKeyRemote(@RequestParam("key") String key);
}
