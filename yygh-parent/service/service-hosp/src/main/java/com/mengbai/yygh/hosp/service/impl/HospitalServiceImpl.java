package com.mengbai.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mengbai.yygh.cmn.client.DictFeignClient;
import com.mengbai.yygh.enums.DictEnum;
import com.mengbai.yygh.hosp.repository.HospitalRepository;
import com.mengbai.yygh.hosp.service.HospitalService;
import com.mengbai.yygh.model.hosp.Hospital;
import com.mengbai.yygh.vo.hosp.HospitalQueryVo;
import com.mysql.jdbc.log.Log;
import com.mysql.jdbc.log.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author zcc
 * @version 2021/04/12/20:30
 * @description <描述>
 * @create 2021/04/12/20:30
 */
@Service
public class HospitalServiceImpl implements HospitalService {

	private Logger logger = LoggerFactory.getLogger(HospitalServiceImpl.class);

	@Autowired
	private HospitalRepository hospitalRepository;

	@Resource
	private DictFeignClient dictFeignClient;


	/***
	 * 上传医院信息
	 * @param parameterMap
	 */
	@Override
	public void save(Map<String, Object> parameterMap) {
		// 把map对象转换成实体类
		String mapParamter = JSONObject.toJSONString(parameterMap);
		// logger.info("mapParamter:" + mapParamter);
		Hospital hospital = JSONObject.parseObject(mapParamter, Hospital.class);
		// logger.info("hospital:" + hospital.toString());
		Hospital targetHospital = hospitalRepository.getHospitalByHoscode(hospital.getHoscode());
		// 如果数据库中有值  执行更新
		if (null != targetHospital) {
			//0：未上线 1：已上线
			hospital.setStatus(targetHospital.getStatus());
			hospital.setCreateTime(targetHospital.getCreateTime());
			hospital.setUpdateTime(new Date());
			hospital.setIsDeleted(0);
			hospitalRepository.save(hospital);

		} else {
			// 如果数据库中没有数据 添加
			hospital.setStatus(0);
			hospital.setCreateTime(new Date());
			hospital.setUpdateTime(new Date());
			hospital.setIsDeleted(0);
			hospitalRepository.save(hospital);

		}


	}

	/***
	 * 根据医院编号查询
	 * @param hoscode
	 * @return
	 */
	@Override
	public Hospital getByHoscode(String hoscode) {
		Hospital hospitalByHoscode = hospitalRepository.getHospitalByHoscode(hoscode);
		return hospitalByHoscode;
	}

	/***
	 * 医院列表（条件查询分页）
	 * @param page
	 * @param limit
	 * @param hospitalQueryVo
	 * @return
	 */
	@Override
	public Page<Hospital> selectHospPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo) {
		//分页
		Pageable pageable = PageRequest.of(page - 1, limit);

		Hospital hospital = new Hospital();
		BeanUtils.copyProperties(hospitalQueryVo, hospital);

		hospital.setIsDeleted(0);
		//条件封装
		ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreCase(true);
		Example<Hospital> example = Example.of(hospital, matcher);
		//执行查询
		Page<Hospital> hospitalPage = hospitalRepository.findAll(example, pageable);

		// 获取查询list集合，遍历进行医院等级封装
		hospitalPage.getContent().stream().forEach(item -> {
			this.packHospital(item);
		});
		return hospitalPage;
	}

	/***
	 * 更新医院上线状态
	 * @param id
	 * @param status
	 */
	@Override
	public void updatStatus(String id, Integer status) {
		//根据ID查询医院信息
		Hospital hospital = hospitalRepository.findById(id).get();
		System.out.println("修改前：" + hospital.getStatus());
		//设置修改的值
		hospital.setStatus(status);
		hospital.setUpdateTime(new Date());
		//保存
		Hospital save = hospitalRepository.save(hospital);
		System.out.println("修改后" + save.getStatus());
	}

	/***
	 * 医院详情
	 * @param id
	 * @return
	 */
	@Override
	public Map<String, Object> show(String id) {
		Map<String, Object> resultMap = new HashMap<>();

		Hospital hospital = this.packHospital(hospitalRepository.findById(id).get());
		//医院基本信息（包含医院等级）
		resultMap.put("hospital", hospital);
		//单独处理更直观
		resultMap.put("bookingRule", hospital.getBookingRule());
		//不需要重复返回
		hospital.setBookingRule(null);

		return resultMap;
	}

	@Override
	public String getHospName(String hoscode) {
		Hospital hospital = hospitalRepository.getHospitalByHoscode(hoscode);
		if (null != hospital) {
			return hospital.getHosname();
		}
		return "";
	}


	/***
	 * 获取查询list集合，遍历进行医院等级封装
	 * @param hospital
	 * @return
	 */
	private Hospital packHospital(Hospital hospital) {
		String hostypeString = dictFeignClient.getName(DictEnum.HOSTYPE.getDictCode(), hospital.getHostype());
		hospital.getParam().put("hostypeString", hostypeString);
		return hospital;
	}
}
