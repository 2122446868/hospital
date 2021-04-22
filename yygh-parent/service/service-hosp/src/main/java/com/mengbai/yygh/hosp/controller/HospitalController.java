package com.mengbai.yygh.hosp.controller;

import com.mengbai.yygh.cmn.client.DictFeignClient;
import com.mengbai.yygh.common.result.Result;
import com.mengbai.yygh.enums.DictEnum;
import com.mengbai.yygh.hosp.service.HospitalService;
import com.mengbai.yygh.model.hosp.Hospital;
import com.mengbai.yygh.vo.hosp.HospitalQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * HospitalController
 * <医院管理接口>
 *
 * @author mb
 * @version [版本号, 2021/4/15/14:22]
 */
@Api(tags = "医院管理接口")
@RestController
// @CrossOrigin
@RequestMapping("/admin/hosp/hospital")
public class HospitalController {

	@Resource
	private HospitalService hospitalService;


	@ApiOperation(value = "医院列表（条件查询分页）")
	@GetMapping("list/{page}/{limit}")
	public Result listHOsp(@PathVariable("page") Integer page,
	                       @PathVariable("limit") Integer limit,
	                       HospitalQueryVo HospitalService) {

		Page<Hospital> hospitalPage = hospitalService.selectHospPage(page, limit, HospitalService);

		return Result.ok(hospitalPage);

	}

	@ApiOperation(value = "更新上线状态")
	@GetMapping("updateStatus/{id}/{status}")
	public Result lock(@PathVariable("id") String id, @PathVariable("status") Integer status) {
		hospitalService.updatStatus(id, status);
		return Result.ok();

	}

	@ApiOperation(value = "医院详情信息")
	@GetMapping("show/{id}")
	public Result show(@PathVariable("id") String id) {
		Map<String, Object> map = hospitalService.show(id);

		return Result.ok(map);

	}


}
