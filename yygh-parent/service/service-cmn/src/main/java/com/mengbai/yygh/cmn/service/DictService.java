package com.mengbai.yygh.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mengbai.yygh.model.cmn.Dict;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface DictService extends IService<Dict> {
	/***
	 * 根据上级ID获取子节点数据列表
	 * @param parentId
	 * @return
	 */
	List<Dict> findByParentId(long parentId);

	/***
	 * 导出数据字典
	 * @param response
	 */
	void exportData(HttpServletResponse response);

	/***
	 * 导入数据字典
	 * @param file
	 */
	void importDictData(MultipartFile file);

	/***
	 * 根据dictCode和value查询
	 * @param dictCode
	 * @param value
	 * @return
	 */
	String getDictName(String dictCode, String value);
}
