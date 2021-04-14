package com.mengbai.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mengbai.yygh.hosp.repository.DepartmentRepository;
import com.mengbai.yygh.hosp.service.DepartmentService;
import com.mengbai.yygh.model.hosp.Department;
import com.mengbai.yygh.vo.hosp.DepartmentQueryVo;
import com.mysql.jdbc.log.Log;
import com.mysql.jdbc.log.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
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

	/***
	 * 上传科室接口
	 * @param parameterMap
	 */
	@Override
	public void saveDepartment(Map<String, Object> parameterMap) {
		// 把parameterMap 转换成实体类对象
		Department targetDepartment = JSONObject.parseObject(JSONObject.toJSONString(parameterMap), Department.class);
		String hoscode = targetDepartment.getHoscode();
		String depcode = targetDepartment.getDepcode();
		// 执行查询 根据医院编号 和可是编号
		Department departmentExists = departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
		// 如果不为空 更新
		if (null != departmentExists) {
			departmentExists.setUpdateTime(new Date());
			departmentExists.setIsDeleted(0);
			departmentRepository.save(departmentExists);

		} else {
			targetDepartment.setUpdateTime(new Date());
			targetDepartment.setCreateTime(new Date());
			targetDepartment.setIsDeleted(0);
			//	如果为空添加
			departmentRepository.save(targetDepartment);
		}

	}

	/***
	 * 查询科室接口
	 * @param page
	 * @param limit
	 * @param departmentQueryVo
	 * @return
	 */
	@Override
	public Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo) {
		Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
		//创建Pageable对象 设置当前页和每页记录数  0是第一页
		Pageable pageable = PageRequest.of(page - 1, limit, sort);

		Department department = new Department();
		BeanUtils.copyProperties(departmentQueryVo, department);
		department.setIsDeleted(0);

		// 创建匹配器，即如何使用查询条件
		ExampleMatcher matcher = ExampleMatcher.matching()//构建对象
				.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)//改变默认字符串匹配方式：模糊查询
				.withIgnoreCase(true); //改变默认大小写忽略方式：忽略大小写
		Example<Department> example = Example.of(department, matcher);

		Page<Department> departmentPage = departmentRepository.findAll(example, pageable);

		return departmentPage;
	}

	/***
	 * 删除科室
	 * @param hoscode
	 * @param depcode
	 */
	@Override
	public void remove(String hoscode, String depcode) {
		Department department = departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
		if (null != department) {
			departmentRepository.deleteById(department.getId());
		}

	}


}
