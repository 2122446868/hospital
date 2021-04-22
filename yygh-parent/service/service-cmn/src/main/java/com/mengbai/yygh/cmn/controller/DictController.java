package com.mengbai.yygh.cmn.controller;

import com.alibaba.excel.util.StringUtils;
import com.mengbai.yygh.cmn.service.DictService;
import com.mengbai.yygh.common.result.Result;
import com.mengbai.yygh.model.cmn.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * CmnController
 * <功能详细描述>
 *
 * @author 赵长春
 * @version [版本号, 2021/4/9 20:51]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Api(tags = "数据字典接口")
@RestController
@RequestMapping("/admin/cmn/dict")
// @CrossOrigin //允许跨域
public class DictController {

	@Autowired
	private DictService dictService;


	@ApiOperation(value = "根据上级ID获取子节点数据列表")
	@GetMapping("findByParentId/{id}")
	public Result findByParentId(@PathVariable("id") long id) {
		List<Dict> dictList = dictService.findByParentId(id);
		return Result.ok(dictList);


	}

	@ApiOperation(value = "导出数据字典")
	@GetMapping("exportDictData")
	public void exportDictData(HttpServletResponse response) {
		dictService.exportData(response);
	}

	@ApiOperation(value = "导入数据字典")
	@PostMapping("importDictData")
	public Result importDictData(MultipartFile file) {
		if (!StringUtils.isEmpty(file)) {
			dictService.importDictData(file);
			return Result.ok();
		}
		return Result.fail("file为空");

	}


	@ApiOperation(value = "根据dictCode和value查询")
	@GetMapping("getName/{dictCode}/{value}")
	public String getName(@PathVariable("dictCode") String dictCode,
	                      @PathVariable("value") String value) {
		String dictName = dictService.getDictName(dictCode, value);
		return dictName;
	}

	@ApiOperation("根据value查询")
	@GetMapping("getName/{value}")
	public String getName(@PathVariable("value") String value) {
		String dictName = dictService.getDictName("", value);
		return dictName;

	}

	@ApiOperation(value = "根据dictCode获取下级子节点")
	@GetMapping("findByDictCode/{dictCode}")
	public Result findByDictCode(@PathVariable("dictCode") String dictCode) {
		List<Dict> dictList = dictService.findByDictCode(dictCode);
		return Result.ok(dictList);

	}

}
