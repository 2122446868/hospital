package com.mengbai.yygh.hosp.controller.API;

import com.mengbai.yygh.hosp.service.HospitalService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
