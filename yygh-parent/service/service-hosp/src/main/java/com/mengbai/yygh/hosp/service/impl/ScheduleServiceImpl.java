package com.mengbai.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mengbai.yygh.hosp.repository.ScheduleRepository;
import com.mengbai.yygh.hosp.service.ScheduleService;
import com.mengbai.yygh.model.hosp.Schedule;
import com.mengbai.yygh.vo.hosp.ScheduleOrderVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @author zcc
 * @version 2021/04/14/14:55
 * @description <描述>
 * @create 2021/04/14/14:55
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {
	@Autowired
	private ScheduleRepository scheduleRepository;


	/***
	 * 上传排班信息
	 * @param parameterMap
	 */
	@Override
	public void saveSchedule(Map<String, Object> parameterMap) {
		// 把parameterMap 转换成实体类Schedule
		Schedule schedule = JSONObject.parseObject(JSONObject.toJSONString(parameterMap), Schedule.class);
		String hoscode = schedule.getHoscode();
		String hosScheduleId = schedule.getHosScheduleId();
		//根据hoscode hosScheduleId 查询信息getDepartmentByHoscodeAndDepcode
		Schedule scheduleExists = scheduleRepository.getScheduleByHoscodeAndHosScheduleId(hoscode, hosScheduleId);
		// 判断如果数据库有 则执行更新
		if (null != scheduleExists) {
			scheduleExists.setUpdateTime(new Date());
			scheduleExists.setIsDeleted(0);
			scheduleRepository.save(scheduleExists);

		} else {
			schedule.setUpdateTime(new Date());
			schedule.setCreateTime(new Date());
			schedule.setIsDeleted(0);

			Schedule save = scheduleRepository.save(schedule);
		}

	}

	/***
	 * 分页查询
	 * @param page
	 * @param limit
	 * @param scheduleOrderVo
	 * @return
	 */
	@Override
	public Page<Schedule> selectPage(int page, int limit, ScheduleOrderVo scheduleOrderVo) {
		//0是第一页  根据创建时间排序
		Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "createTime"));
		Schedule schdule = new Schedule();
		BeanUtils.copyProperties(scheduleOrderVo, schdule);
		schdule.setIsDeleted(0);
		//创建匹配器，即如何使用查询条件
		ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreCase(true);
		//创建实例
		Example<Schedule> example = Example.of(schdule, matcher);
		//执行查询
		Page<Schedule> schedulePage = scheduleRepository.findAll(example, pageable);
		return schedulePage;
	}

	/***
	 * 删除排班
	 * @param hoscode
	 * @param hosScheduleId
	 */
	@Override
	public void removeSchedule(String hoscode, String hosScheduleId) {
		Schedule schedule = scheduleRepository.getScheduleByHoscodeAndHosScheduleId(hoscode, hosScheduleId);
		if (null != schedule) {
			scheduleRepository.deleteById(schedule.getId());
		}


	}
}
