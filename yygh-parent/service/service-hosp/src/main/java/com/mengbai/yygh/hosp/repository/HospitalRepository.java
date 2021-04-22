package com.mengbai.yygh.hosp.repository;

import com.mengbai.yygh.model.hosp.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zcc
 * @version 2021/04/12/20:25
 * @description <描述>
 * @create 2021/04/12/20:25
 */
@Repository
public interface HospitalRepository extends MongoRepository<Hospital,String> {
	Hospital getHospitalByHoscode(String hoscode);

	List<Hospital> findHospitalByHosnameLike(String hosname);
}
