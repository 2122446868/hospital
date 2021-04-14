package com.mengbai.yygh.hosp.repository;

import com.mengbai.yygh.model.hosp.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zcc
 * @version 2021/04/14/14:54
 * @description <描述>
 * @create 2021/04/14/14:54
 */
@Repository
public interface ScheduleRepository  extends MongoRepository<Schedule,String> {


	Schedule getScheduleByHoscodeAndHosScheduleId(String hoscode, String hosScheduleId);
}
