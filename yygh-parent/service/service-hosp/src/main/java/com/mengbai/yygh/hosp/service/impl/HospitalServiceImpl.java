package com.mengbai.yygh.hosp.service.impl;

import com.mengbai.yygh.hosp.repository.HospitalRepository;
import com.mengbai.yygh.hosp.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zcc
 * @version 2021/04/12/20:30
 * @description <描述>
 * @create 2021/04/12/20:30
 */
@Service
public class HospitalServiceImpl implements HospitalService {

	@Autowired
	private HospitalRepository hospitalRepository;
}
