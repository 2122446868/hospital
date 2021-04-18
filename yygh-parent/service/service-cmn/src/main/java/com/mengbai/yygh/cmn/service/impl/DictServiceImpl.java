package com.mengbai.yygh.cmn.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mengbai.yygh.cmn.listener.DictListener;
import com.mengbai.yygh.cmn.mapper.DictMapper;
import com.mengbai.yygh.cmn.service.DictService;
import com.mengbai.yygh.model.cmn.Dict;
import com.mengbai.yygh.vo.cmn.DictEeVo;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * CmnServiceImpl
 * <功能详细描述>
 *
 * @author 赵长春
 * @version [版本号, 2021/4/9 20:42]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {


	/***
	 * 根据上级ID获取子节点数据列表
	 * @param parentId
	 * @return
	 */
	@Cacheable(value = "dict", keyGenerator = "keyGenerator")
	@Override
	public List<Dict> findByParentId(long parentId) {
//        封装条件
		QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("parent_id", parentId);
//        执行查询
		List<Dict> dictList = baseMapper.selectList(queryWrapper);
//        判断ID下面是否有子节点
		for (Dict dict : dictList) {
			Long id = dict.getId();
			boolean children = this.isChildren(id);
			dict.setHasChildren(children);


		}
		return dictList;
	}

	/***
	 * 导出
	 * @param response
	 */
	@Override
	public void exportData(HttpServletResponse response) {
//        设置导出的类型
		response.setContentType("application/vnd.ms-excel");
//        设置字符集
		response.setCharacterEncoding("UTF-8");
		String fileName = "dict";
//        设置响应头
		response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
//        查询数据
		List<Dict> dictList = baseMapper.selectList(null);
//        数据转换
		List<DictEeVo> dictEeVoList = new ArrayList<>(dictList.size());
		for (Dict dict : dictList
		) {
			DictEeVo dictEeVo = new DictEeVo();
			BeanUtils.copyProperties(dict, dictEeVo);
			dictEeVoList.add(dictEeVo);

		}


//        调用方法进行写操作
		try {
			EasyExcel.write(response.getOutputStream(), DictEeVo.class)
					.sheet("数据字典")
					.doWrite(dictEeVoList);
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	/***
	 * 导入数据字典
	 *  allEntries = true: 方法调用后清空所有缓存
	 * @param file
	 */
	@CacheEvict(value = "dict", allEntries = true)
	@Override
	public void importDictData(MultipartFile file) {
		try {
			EasyExcel.read(file.getInputStream(), DictEeVo.class, new DictListener(baseMapper)).sheet().doRead();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	/***
	 * 根据dictCode和value查询
	 * @param dictCode
	 * @param value
	 * @return
	 */
	@Override
	public String getDictName(String dictCode, String value) {
		// 如果dictCode为空 根据value查询
		if (StringUtils.isEmpty(dictCode)) {
			QueryWrapper<Dict> wrapper = new QueryWrapper<>();
			wrapper.eq("value", value);
			Dict dict = baseMapper.selectOne(wrapper);
			return dict.getName();
		} else { //如果dictCode不为空 根据dictCode和value查询
			QueryWrapper<Dict> wrapper = new QueryWrapper<>();
			wrapper.eq("dict_code", dictCode);
			Dict codeDict = baseMapper.selectOne(wrapper);
			Long parentId = codeDict.getId();
			Dict finalDict = baseMapper.selectOne(new QueryWrapper<Dict>().eq("parent_id", parentId).eq("value", value));


			return finalDict.getName();
		}
	}

	/***
	 * 根据dictCode获取下级子节点
	 * @param dictCode
	 * @return
	 */
	@Override
	public List<Dict> findByDictCode(String dictCode) {

		QueryWrapper<Dict> wrapper = new QueryWrapper<>();
		wrapper.eq("dict_code", dictCode);
		Dict codeDict = baseMapper.selectOne(wrapper);
		Long parentId = codeDict.getId();
		List<Dict> dictList = this.findByParentId(parentId);
		return dictList;
	}

	/***
	 * 判断ID下面是否有子节点
	 * @param id
	 * @return
	 */
	private boolean isChildren(long id) {
//      封装条件
		QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("parent_id", id);
		Integer count = baseMapper.selectCount(queryWrapper);
		return count > 0;

	}

}
