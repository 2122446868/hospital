package com.mengbai.yygh.hosp.repository;

import com.mengbai.yygh.model.hosp.Department;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zcc
 * @version 2021/04/13/17:35
 * @description <描述>
 * @create 2021/04/13/17:35
 */
@Repository
public interface DepartmentRepository extends MongoRepository<Department, String> {

	Department getDepartmentByHoscodeAndDepcode(String hoscode, String depcode);
}
