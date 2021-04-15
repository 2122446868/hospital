package com.mengbai.yygh.cmn.client;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * DictFeignClient
 * <医院管理远程接口调用>
 *
 * @author mb
 * @version [版本号, 2021/4/15/15:55]
 */
@Repository
@FeignClient("service-cmn")
public interface DictFeignClient {

	@ApiOperation(value = "根据dictCode和value查询")
	@GetMapping(value = "/admin/cmn/dict/getName/{dictCode}/{value}")
	public String getName(@PathVariable("dictCode") String dictCode,
	                      @PathVariable("value") String value);


	@ApiOperation("根据value查询")
	@GetMapping(value = "/admin/cmn/dict/getName/{value}")
	public String getName(@PathVariable("value") String value);


}
