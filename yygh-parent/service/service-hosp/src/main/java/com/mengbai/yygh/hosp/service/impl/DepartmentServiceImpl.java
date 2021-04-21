package com.mengbai.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mengbai.yygh.hosp.repository.DepartmentRepository;
import com.mengbai.yygh.hosp.service.DepartmentService;
import com.mengbai.yygh.model.hosp.Department;
import com.mengbai.yygh.vo.hosp.DepartmentQueryVo;
import com.mengbai.yygh.vo.hosp.DepartmentVo;
import com.mysql.jdbc.log.Log;
import com.mysql.jdbc.log.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

	/***
	 * 根据医院编号，查询医院所有科室列表
	 * @param hoscode
	 * @return
	 */
	@Override
	public List<DepartmentVo> findDeptTree(String hoscode) {
		//创建list集合，用于最终数据封装
		List<DepartmentVo> result = new ArrayList<>();

		//根据医院编号，查询医院所有科室信息
		Department departmentQuery = new Department();
		departmentQuery.setHoscode(hoscode);
		Example example = Example.of(departmentQuery);
		//所有科室列表 departmentList
		List<Department> departmentList = departmentRepository.findAll(example);

		//根据大科室编号  bigcode 分组，获取每个大科室里面下级子科室
		Map<String, List<Department>> deparmentMap =
				departmentList.stream().collect(Collectors.groupingBy(Department::getBigcode));
		//遍历map集合 deparmentMap
		for (Map.Entry<String, List<Department>> entry : deparmentMap.entrySet()) {
			//大科室编号
			String bigcode = entry.getKey();
			//大科室编号对应的全局数据
			List<Department> deparment1List = entry.getValue();
			//封装大科室
			DepartmentVo departmentVo1 = new DepartmentVo();
			departmentVo1.setDepcode(bigcode);
			departmentVo1.setDepname(deparment1List.get(0).getBigname());

			//封装小科室
			List<DepartmentVo> children = new ArrayList<>();
			for (Department department : deparment1List) {
				DepartmentVo departmentVo2 = new DepartmentVo();
				departmentVo2.setDepcode(department.getDepcode());
				departmentVo2.setDepname(department.getDepname());
				//封装到list集合
				children.add(departmentVo2);
			}
			//把小科室list集合放到大科室children里面
			departmentVo1.setChildren(children);
			//放到最终result里面
			result.add(departmentVo1);
		}
		//返回
		return result;
	}

	@Override
	public Object getDepName(String hoscode, String depcode) {
		Department department = departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
		if(department != null) {
			return department.getDepname();
		}
		return null;
	}


}
