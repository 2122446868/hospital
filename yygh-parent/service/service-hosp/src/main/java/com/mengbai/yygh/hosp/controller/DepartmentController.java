package com.mengbai.yygh.hosp.controller;

import com.mengbai.yygh.common.result.Result;
import com.mengbai.yygh.hosp.service.DepartmentService;
import com.mengbai.yygh.vo.hosp.DepartmentVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.List;

/**
 * DepartmentController
 * <排班管理实现>
 *
 * @author mb
 * @version [版本号, 2021/4/20/9:55]
 */
@Api(tags = "排班管理实现")
@RestController
@CrossOrigin
@RequestMapping("/admin/hosp/department")
public class DepartmentController {

	@Resource
	private DepartmentService departmentService;

	/***
	 * 根据医院编号，查询医院所有科室列表
	 *
	 * @return
	 */
	@ApiOperation(value = "查询医院所有科室列表")
	@GetMapping("getDepartList/{hoscode}")
	public Result getDepartList(@PathVariable("hoscode") String hoscode) {
		List<DepartmentVo> departmentVoList = departmentService.findDeptTree(hoscode);
		return Result.ok(departmentVoList);

	}


}

