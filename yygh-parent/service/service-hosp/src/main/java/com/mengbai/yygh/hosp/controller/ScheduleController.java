package com.mengbai.yygh.hosp.controller;

import com.mengbai.yygh.common.result.Result;
import com.mengbai.yygh.hosp.service.ScheduleService;
import com.mengbai.yygh.model.hosp.Schedule;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * ScheduleController
 * <功能描述>
 *
 * @author mb
 * @version [版本号, 2021/4/21/13:47]
 */

@RestController
// @CrossOrigin
@RequestMapping("/admin/hosp/schedule")
public class ScheduleController {

	@Resource
	private ScheduleService scheduleService;


	/***
	 * 根据医院编号 和 科室编号 ，查询排班规则数据
	 * @param page
	 * @param limit
	 * @param hoscode
	 * @param depcode
	 * @return
	 */
	@ApiOperation(value ="查询排班规则数据")
	@GetMapping("getScheduleRule/{page}/{limit}/{hoscode}/{depcode}")
	public Result getScheduleRule(@PathVariable long page,
	                              @PathVariable long limit,
	                              @PathVariable String hoscode,
	                              @PathVariable String depcode) {
		Map<String,Object> map
				= scheduleService.getRuleSchedule(page,limit,hoscode,depcode);
		return Result.ok(map);
	}


	/***
	 * 根据医院编号 、科室编号和工作日期，查询排班详细信息
	 */
	@ApiOperation(value = "查询排班详细信息")
	@GetMapping("getScheduleDetail/{hoscode}/{depcode}/{workDate}")
	public Result getScheduleDetail( @PathVariable String hoscode,
	                                 @PathVariable String depcode,
	                                 @PathVariable String workDate) {
		List<Schedule> list = scheduleService.getDetailSchedule(hoscode,depcode,workDate);
		return Result.ok(list);
	}
}
