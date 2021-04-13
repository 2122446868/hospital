package com.mengbai.yygh.hosp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mengbai.yygh.hosp.mapper.HospitalSetMapper;
import com.mengbai.yygh.hosp.service.HospitalSetService;
import com.mengbai.yygh.model.hosp.HospitalSet;
import org.springframework.stereotype.Service;

/**
 * HospitalSetServiceImpl
 * <功能详细描述>
 *
 * @author 赵长春
 * @version [版本号, 2021/4/6 14:03]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
public class HospitalSetServiceImpl extends ServiceImpl<HospitalSetMapper, HospitalSet> implements HospitalSetService {

	/***
	 * 根据传递过来的医院编码 在数据库查询签名
	 * @param hoscode
	 * @return
	 */
	@Override
	public String findBySign(String hoscode) {
		//封装条件
		QueryWrapper<HospitalSet> queryWrapper = new QueryWrapper();
		queryWrapper.eq("hoscode", hoscode);
		//执行查询
		HospitalSet hospitalSet = baseMapper.selectOne(queryWrapper);
		//返回
		return hospitalSet.getSignKey();
	}
}
