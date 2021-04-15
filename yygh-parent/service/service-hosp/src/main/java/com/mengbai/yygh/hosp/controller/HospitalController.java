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

/**
 * HospitalController
 * <功能描述>
 *
 * @author mb
 * @version [版本号, 2021/4/15/14:22]
 */
@Api(tags = "医院管理接口")
@RestController
@CrossOrigin
@RequestMapping("/admin/hosp/hospital")
public class HospitalController {
	@Autowired
	private HospitalService hospitalService;

	@Autowired
	private DictFeignClient dictFeignClient;

	@ApiOperation(value = "医院列表（条件查询分页）")
	@GetMapping("list/{page}/{limit}")
	public Result listHOsp(@PathVariable("page") Integer page,
	                       @PathVariable("limit") Integer limit,
	                       HospitalQueryVo HospitalService) {

		Page<Hospital> hospitalPage = hospitalService.selectHospPage(page, limit, HospitalService);


		hospitalPage.getContent().stream().forEach(item -> {
			this.packHospital(item);
		});
		return Result.ok(hospitalPage);

	}

	/***
	 * 获取查询list集合，遍历进行医院等级封装
	 * @param hospital
	 * @return
	 */
	private Hospital packHospital(Hospital hospital) {
		String hostypeString = dictFeignClient.getName(DictEnum.HOSTYPE.getDictCode(), hospital.getHostype());
		hospital.getParam().put("hostypeString", hostypeString);
		return hospital;
	}


}
