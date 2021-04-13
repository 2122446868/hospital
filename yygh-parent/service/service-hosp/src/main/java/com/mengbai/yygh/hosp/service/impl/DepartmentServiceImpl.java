package com.mengbai.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mengbai.yygh.hosp.repository.DepartmentRepository;
import com.mengbai.yygh.hosp.service.DepartmentService;
import com.mengbai.yygh.model.hosp.Department;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author zcc
 * @version 2021/04/13/17:37
 * @description <描述>
 * @create 2021/04/13/17:37
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;

	@Override
	public void saveDepartment(Map<String, Object> parameterMap) {
		// 把parameterMap 转换成实体类对象
		Department targetDepartment = JSONObject.parseObject(JSONObject.toJSONString(parameterMap), Department.class);
		String hoscode = targetDepartment.getHoscode();
		String depcode = targetDepartment.getDepcode();
		// 执行查询
		Department department = departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
		if (null != department){
			// TODO 未完成
		}

	}
}
