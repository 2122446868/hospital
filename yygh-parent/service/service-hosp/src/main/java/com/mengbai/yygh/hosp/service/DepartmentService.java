package com.mengbai.yygh.hosp.service;

import java.util.Map;

/**
 * @author zcc
 * @version 2021/04/13/17:37
 * @description <描述>
 * @create 2021/04/13/17:37
 */
public interface DepartmentService {
	/***
	 * 上传科室信息
	 * @param parameterMap
	 */
	void saveDepartment(Map<String, Object> parameterMap);
}
