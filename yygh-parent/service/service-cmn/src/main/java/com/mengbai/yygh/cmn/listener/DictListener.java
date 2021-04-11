package com.mengbai.yygh.cmn.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.mengbai.yygh.cmn.mapper.DictMapper;
import com.mengbai.yygh.model.cmn.Dict;
import com.mengbai.yygh.vo.cmn.DictEeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zcc
 * @version 2021/04/11/13:03
 * @description <描述>
 * @create 2021/04/11/13:03
 */
public class DictListener extends AnalysisEventListener<DictEeVo> {

	private DictMapper dictMapper;

	public DictListener(DictMapper dictMapper) {
		this.dictMapper = dictMapper;
	}

	/***
	 * 一行一行读取
	 * @param dictEeVo
	 * @param analysisContext
	 */
	@Override
	public void invoke(DictEeVo dictEeVo, AnalysisContext analysisContext) {
//        调用方法添加数据库
		Dict dict = new Dict();
		BeanUtils.copyProperties(dictEeVo, dict);
		dictMapper.insert(dict);


	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext analysisContext) {

	}
}
