package com.mengbai.yygh.hosp.controller.API;

import com.mengbai.yygh.common.result.Result;
import com.mengbai.yygh.hosp.service.HospitalService;
import com.mengbai.yygh.model.hosp.Hospital;
import com.mengbai.yygh.vo.hosp.HospitalQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HospitalApiController
 * <功能描述>
 *
 * @author mb
 * @version [版本号, 2021/4/22/17:47]
 */
@Api(tags = "医院管理接口")
@RestController
@RequestMapping("/api/hosp/hospital")
public class HospitalApiController {

	@Autowired
	private HospitalService hospitalService;


	@ApiOperation(value = "获取分页列表")
	@GetMapping("{page}/{limit}")
	public Result index(@PathVariable Integer page,
	                    @PathVariable Integer limit,
	                    HospitalQueryVo hospitalQueryVo) {
		Page<Hospital> hospitalPage = hospitalService.selectHospPage(page, limit, hospitalQueryVo);

		return Result.ok(hospitalPage);
	}

}
