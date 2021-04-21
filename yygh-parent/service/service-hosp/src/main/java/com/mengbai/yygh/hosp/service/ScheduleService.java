package com.mengbai.yygh.hosp.service;

import com.mengbai.yygh.model.hosp.Schedule;
import com.mengbai.yygh.vo.hosp.ScheduleOrderVo;
import org.springframework.data.domain.Page;

import java.util.List;
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

	/***
	 * 根据医院编号 和 科室编号 ，查询排班规则数据
	 * @param page
	 * @param limit
	 * @param hoscode
	 * @param depcode
	 * @return
	 */
	Map<String, Object> getRuleSchedule(long page, long limit, String hoscode, String depcode);

	/***
	 * 根据医院编号 、科室编号和工作日期，查询排班详细信息
	 * @param hoscode
	 * @param depcode
	 * @param workDate
	 * @return
	 */
	List<Schedule> getDetailSchedule(String hoscode, String depcode, String workDate);
}
