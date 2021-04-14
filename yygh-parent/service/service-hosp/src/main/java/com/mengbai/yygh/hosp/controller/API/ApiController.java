package com.mengbai.yygh.hosp.controller.API;

import com.mengbai.yygh.common.exception.YyghException;
import com.mengbai.yygh.common.helper.HttpRequestHelper;
import com.mengbai.yygh.common.result.Result;
import com.mengbai.yygh.common.result.ResultCodeEnum;
import com.mengbai.yygh.common.util.MD5;
import com.mengbai.yygh.hosp.repository.DepartmentRepository;
import com.mengbai.yygh.hosp.service.DepartmentService;
import com.mengbai.yygh.hosp.service.HospitalService;
import com.mengbai.yygh.hosp.service.HospitalSetService;
import com.mengbai.yygh.hosp.service.ScheduleService;
import com.mengbai.yygh.model.hosp.Department;
import com.mengbai.yygh.model.hosp.Hospital;
import com.mengbai.yygh.model.hosp.Schedule;
import com.mengbai.yygh.vo.hosp.DepartmentQueryVo;
import com.mengbai.yygh.vo.hosp.DepartmentVo;
import com.mengbai.yygh.vo.hosp.ScheduleOrderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author zcc
 * @version 2021/04/12/20:32
 * @description <描述>
 * @create 2021/04/12/20:32
 */
@Api(tags = "医院管理API接口")
@RestController
@RequestMapping("/api/hosp")
public class ApiController {

	private Logger logger = LoggerFactory.getLogger(ApiController.class);

	@Autowired
	private HospitalService hospitalService;

	@Autowired
	private HospitalSetService hospitalSetService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private ScheduleService scheduleService;


	@ApiOperation(value = "上传医院")
	@PostMapping("saveHospital")
	public Result saveHospital(HttpServletRequest request) {
		//获取请求参数
		Map<String, String[]> requestParameterMap = request.getParameterMap();
		// 调用工具类HttpRequestHelper 对Map<String, String[]>进行遍历处理
		Map<String, Object> parameterMap = HttpRequestHelper.switchMap(requestParameterMap);

		// 获取医院系统传递过来的签名  签名进行MD5加密
		String hospSign = (String) parameterMap.get("sign");
		String hoscode = (String) parameterMap.get("hoscode");
		//根据传递过来的医院编码 在数据库查询签名
		String signKey = hospitalSetService.findBySign(hoscode);
		//MD5加密
		String MD5encryptSignKey = MD5.encrypt(signKey);
		//判断密码是否一致
		if (!hospSign.equals(MD5encryptSignKey)) {
			throw new YyghException(ResultCodeEnum.SIGN_ERROR);
		}
		// 传输过程中 “+”转换为了“ ”，英因此我们要转换回来
		String logoData = (String) parameterMap.get("logoData");
		logoData.replaceAll(" ", "+");
		parameterMap.put("logoData", logoData);
		hospitalService.save(parameterMap);


		return Result.ok();
	}


	@ApiOperation(value = "查询医院")
	@PostMapping("hospital/show")
	public Result getHOspital(HttpServletRequest request) {
		//获取请求参数
		Map<String, String[]> requestParameterMap = request.getParameterMap();
		// 调用工具类HttpRequestHelper 对Map<String, String[]>进行遍历处理
		Map<String, Object> parameterMap = HttpRequestHelper.switchMap(requestParameterMap);
		// 获取医院系统传递过来的签名  签名进行MD5加密
		String hospSign = (String) parameterMap.get("sign");
		String hoscode = (String) parameterMap.get("hoscode");
		//根据传递过来的医院编码 在数据库查询签名
		String signKey = hospitalSetService.findBySign(hoscode);
		//MD5加密
		String MD5encryptSignKey = MD5.encrypt(signKey);
		//判断密码是否一致
		if (!hospSign.equals(MD5encryptSignKey)) {
			throw new YyghException(ResultCodeEnum.SIGN_ERROR);
		}
		Hospital hospital = hospitalService.getByHoscode(hoscode);
		return Result.ok(hospital);


	}


	@ApiOperation(value = "上传科室")
	@PostMapping("saveDepartment")
	public Result saveDepartment(HttpServletRequest request) {
		//获取请求参数
		Map<String, String[]> requestParameterMap = request.getParameterMap();
		// 调用工具类HttpRequestHelper 对Map<String, String[]>进行遍历处理
		Map<String, Object> parameterMap = HttpRequestHelper.switchMap(requestParameterMap);
		// 获取医院系统传递过来的签名  签名进行MD5加密
		String hospSign = (String) parameterMap.get("sign");
		String hoscode = (String) parameterMap.get("hoscode");
		//根据传递过来的医院编码 在数据库查询签名
		String signKey = hospitalSetService.findBySign(hoscode);
		//MD5加密
		String MD5encryptSignKey = MD5.encrypt(signKey);
		//判断密码是否一致
		if (!hospSign.equals(MD5encryptSignKey)) {
			throw new YyghException(ResultCodeEnum.SIGN_ERROR);
		}
		departmentService.saveDepartment(parameterMap);
		return Result.ok();
	}

	@ApiOperation(value = "查询科室")
	@PostMapping("department/list")
	public Result getDepartment(HttpServletRequest request) {
		//获取请求参数
		Map<String, String[]> requestParameterMap = request.getParameterMap();
		// 调用工具类HttpRequestHelper 对Map<String, String[]>进行遍历处理
		Map<String, Object> parameterMap = HttpRequestHelper.switchMap(requestParameterMap);
		// 获取医院系统传递过来的签名  签名进行MD5加密
		String hospSign = (String) parameterMap.get("sign");
		//医院编号
		String hoscode = (String) parameterMap.get("hoscode");
		//当前页
		int page = StringUtils.isEmpty(parameterMap.get("page")) ? 1 : Integer.parseInt((String) parameterMap.get("page"));
		//每页记录数
		int limit = StringUtils.isEmpty(parameterMap.get("limit")) ? 1 : Integer.parseInt((String) parameterMap.get("limit"));
		//根据传递过来的医院编码 在数据库查询签名
		String signKey = hospitalSetService.findBySign(hoscode);
		//MD5加密
		String MD5encryptSignKey = MD5.encrypt(signKey);
		//判断密码是否一致
		if (!hospSign.equals(MD5encryptSignKey)) {
			throw new YyghException(ResultCodeEnum.SIGN_ERROR);
		}
		DepartmentQueryVo departmentQueryVo = new DepartmentQueryVo();
		departmentQueryVo.setHoscode(hoscode);
		//调用方法查询科室
		Page<Department> departmentPage = departmentService.findPageDepartment(page, limit, departmentQueryVo);
		logger.info("查询科室信息：" + departmentPage);


		return Result.ok(departmentPage);
	}

	@ApiOperation(value = "删除科室")
	@PostMapping("department/remove")
	public Result removeDepartment(HttpServletRequest request) {
		//获取请求参数
		Map<String, String[]> requestParameterMap = request.getParameterMap();
		// 调用工具类HttpRequestHelper 对Map<String, String[]>进行遍历处理
		Map<String, Object> parameterMap = HttpRequestHelper.switchMap(requestParameterMap);
		// 获取医院系统传递过来的签名  签名进行MD5加密
		String hospSign = (String) parameterMap.get("sign");
		//医院编号
		String hoscode = (String) parameterMap.get("hoscode");
		//科室编号
		String depcode = (String) parameterMap.get("depcode");
		//根据传递过来的医院编码 在数据库查询签名
		String signKey = hospitalSetService.findBySign(hoscode);
		//MD5加密
		String MD5encryptSignKey = MD5.encrypt(signKey);
		//判断密码是否一致
		if (!hospSign.equals(MD5encryptSignKey)) {
			throw new YyghException(ResultCodeEnum.SIGN_ERROR);
		}
		departmentService.remove(hoscode, depcode);
		return Result.ok();

	}


	@ApiOperation(value = "上传排班")
	@PostMapping("saveSchedule")
	public Result saveSchedule(HttpServletRequest request) {
		//获取请求参数
		Map<String, String[]> requestParameterMap = request.getParameterMap();
		// 调用工具类HttpRequestHelper 对Map<String, String[]>进行遍历处理
		Map<String, Object> parameterMap = HttpRequestHelper.switchMap(requestParameterMap);

		// 获取医院系统传递过来的签名  签名进行MD5加密
		String hospSign = (String) parameterMap.get("sign");
		//医院编号
		String hoscode = (String) parameterMap.get("hoscode");
		//科室编号
		String depcode = (String) parameterMap.get("depcode");
		//根据传递过来的医院编码 在数据库查询签名
		String signKey = hospitalSetService.findBySign(hoscode);
		//MD5加密
		String MD5encryptSignKey = MD5.encrypt(signKey);
		//判断密码是否一致
		if (!hospSign.equals(MD5encryptSignKey)) {
			throw new YyghException(ResultCodeEnum.SIGN_ERROR);
		}


		scheduleService.saveSchedule(parameterMap);
		return Result.ok();

	}

	@ApiOperation(value = "获取排班分页列表")
	@PostMapping("schedule/list")
	public Result schedule(HttpServletRequest request) {
		//获取请求参数
		Map<String, String[]> requestParameterMap = request.getParameterMap();
		// 调用工具类HttpRequestHelper 对Map<String, String[]>进行遍历处理
		Map<String, Object> parameterMap = HttpRequestHelper.switchMap(requestParameterMap);

		// 获取医院系统传递过来的签名  签名进行MD5加密
		String hospSign = (String) parameterMap.get("sign");
		//医院编号
		String hoscode = (String) parameterMap.get("hoscode");
		int page = StringUtils.isEmpty(parameterMap.get("hoscode")) ? 1 : Integer.parseInt((String) parameterMap.get("page"));
		int limit = StringUtils.isEmpty(parameterMap.get("limit")) ? 1 : Integer.parseInt((String) parameterMap.get("limit"));
		//根据传递过来的医院编码 在数据库查询签名
		String signKey = hospitalSetService.findBySign(hoscode);
		//MD5加密
		String MD5encryptSignKey = MD5.encrypt(signKey);
		//判断密码是否一致
		if (!hospSign.equals(MD5encryptSignKey)) {
			throw new YyghException(ResultCodeEnum.SIGN_ERROR);
		}
		ScheduleOrderVo scheduleOrderVo = new ScheduleOrderVo();
		scheduleOrderVo.setHoscode(hoscode);
		Page<Schedule> schedulePage = scheduleService.selectPage(page, limit, scheduleOrderVo);
		return Result.ok(schedulePage);

	}

	@ApiOperation(value = "删除排班")
	@PostMapping("schedule/remove")
	public Result removeSchedule(HttpServletRequest request) {
		//获取请求参数
		Map<String, String[]> requestParameterMap = request.getParameterMap();
		// 调用工具类HttpRequestHelper 对Map<String, String[]>进行遍历处理
		Map<String, Object> parameterMap = HttpRequestHelper.switchMap(requestParameterMap);
// 获取医院系统传递过来的签名  签名进行MD5加密
		String hospSign = (String) parameterMap.get("sign");
		//医院编号
		String hoscode = (String) parameterMap.get("hoscode");
		//排班编号
		String hosScheduleId = (String) parameterMap.get("hosScheduleId");
		//根据传递过来的医院编码 在数据库查询签名
		String signKey = hospitalSetService.findBySign(hoscode);
		//MD5加密
		String MD5encryptSignKey = MD5.encrypt(signKey);
		//判断密码是否一致
		if (!hospSign.equals(MD5encryptSignKey)) {
			throw new YyghException(ResultCodeEnum.SIGN_ERROR);
		}
		scheduleService.removeSchedule(hoscode,hosScheduleId);
		return Result.ok();

	}
}


