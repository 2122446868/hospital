package com.mengbai.yygh.hosp.service;

import com.mengbai.yygh.model.hosp.Schedule;
import com.mengbai.yygh.vo.hosp.ScheduleOrderVo;
import org.springframework.data.domain.Page;

import java.util.Map;

/**
 * @author zcc
 * @version 2021/04/14/14:55
 * @description <描述>
 * @create 2021/04/14/14:55
 */
public interface ScheduleService {
	/***
	 * 上传排班信息
	 * @param parameterMap
	 */
	void saveSchedule(Map<String, Object> parameterMap);

	/***
	 * 排班分页查询
	 * @param page
	 * @param limit
	 * @param scheduleOrderVo
	 * @return
	 */
	Page<Schedule> selectPage(int page, int limit, ScheduleOrderVo scheduleOrderVo);

	/***
	 * 删除排班
	 * @param hoscode
	 * @param hosScheduleId
	 */
	void removeSchedule(String hoscode, String hosScheduleId);
}
