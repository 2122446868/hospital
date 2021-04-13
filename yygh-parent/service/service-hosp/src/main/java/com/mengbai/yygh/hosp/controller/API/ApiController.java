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
import com.mengbai.yygh.model.hosp.Hospital;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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

	@Autowired
	private HospitalService hospitalService;

	@Autowired
	private HospitalSetService hospitalSetService;

	@Autowired
	private DepartmentService departmentService;


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


}


