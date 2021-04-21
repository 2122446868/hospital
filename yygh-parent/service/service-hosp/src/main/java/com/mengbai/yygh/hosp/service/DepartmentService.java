package com.mengbai.yygh.hosp.service;

import com.mengbai.yygh.model.hosp.Department;
import com.mengbai.yygh.vo.hosp.DepartmentQueryVo;
import com.mengbai.yygh.vo.hosp.DepartmentVo;
import org.springframework.data.domain.Page;

import java.util.List;
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

	/***
	 * 查询科室接口
	 * @param page
	 * @param limit
	 * @param departmentQueryVo
	 * @return
	 */
	Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo);

	/***
	 * 删除科室
	 * @param hoscode
	 * @param depcode
	 */
	void remove(String hoscode, String depcode);

	/***
	 * 据医院编号，查询医院所有科室列表
	 * @param hoscode
	 * @return
	 */
	List<DepartmentVo> findDeptTree(String hoscode);

	Object getDepName(String hoscode, String depcode);
}