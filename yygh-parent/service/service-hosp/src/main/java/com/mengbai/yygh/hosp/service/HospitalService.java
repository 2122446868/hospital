package com.mengbai.yygh.hosp.service;

import com.mengbai.yygh.model.hosp.Hospital;

import java.util.Map;

/**
 * @author zcc
 * @version 2021/04/12/20:30
 * @description <描述>
 * @create 2021/04/12/20:30
 */
public interface HospitalService {
	/***
	 * 上传医院信息
	 * @param parameterMap
	 */
	void save(Map<String, Object> parameterMap);

	/***
	 *
	 根据医院编号查询
	 */
	Hospital getByHoscode(String hoscode);
}
