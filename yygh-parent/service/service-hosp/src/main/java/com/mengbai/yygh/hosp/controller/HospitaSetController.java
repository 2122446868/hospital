package com.mengbai.yygh.hosp.controller;

import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mengbai.yygh.common.result.Result;
import com.mengbai.yygh.common.util.MD5;
import com.mengbai.yygh.hosp.service.HospitalSetService;
import com.mengbai.yygh.model.hosp.HospitalSet;
import com.mengbai.yygh.vo.hosp.HospitalSetQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.apache.logging.log4j.message.ReusableMessage;
import org.apache.poi.ss.formula.udf.UDFFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.RandomAccess;

/**
 * HospitaSetController
 * <功能详细描述>
 *
 * @author 赵长春
 * @version [版本号, 2021/4/6 13:59]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Api(tags = "医院设置管理")
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
@CrossOrigin //允许跨域
public class HospitaSetController {

    @Autowired
    private HospitalSetService hospitalSetService;


    /***
     * 查询医院设置表所有信息
     * @return
     */
    @ApiOperation("获取所有医院设置")
    @GetMapping("findAll")
    public Result findAllHospitalSet() {
        List<HospitalSet> list = hospitalSetService.list();
        return Result.ok(list);
    }

    /***
     * 根据ID删除
     * @param id
     * @return
     */
    @ApiOperation("逻辑删除医院设置")
    @DeleteMapping("{id}")
    public Result removeHospSet(@PathVariable("id") long id) {
        boolean flag = hospitalSetService.removeById(id);
        if (flag) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @ApiOperation("条件分页查询")
    @PostMapping("findPage/{current}/{limit}")
    public Result findPageHospSet(@PathVariable("current") @ApiParam("当前页") long current,
                                  @PathVariable("limit") @ApiParam("每页记录数") long limit,
                                  @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo) {
//        创建page对象，传递当前页 每页记录数
        Page<HospitalSet> page = new Page<>(current, limit);
//        构建条件
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        String hoscode = hospitalSetQueryVo.getHoscode();
        String hosname = hospitalSetQueryVo.getHosname();
        if (!StringUtils.isEmpty(hosname)) {

            wrapper.like("hosname", hosname);
        }
        if (!StringUtils.isEmpty(hoscode)) {

            wrapper.eq("hoscode", hoscode);

        }
//        调用方法实现分页查询
        Page<HospitalSet> pageHospitalSet = hospitalSetService.page(page, wrapper);
//       返回结果
        return Result.ok(pageHospitalSet);
    }

    @ApiOperation("添加医院设置")
    @PostMapping("saveHospitalSet")
    public Result saveHospitalSet(@RequestBody HospitalSet hospitalSet) {
//        设置状态1使用   0 不能使用
        hospitalSet.setStatus(1);
//        签名秘钥
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis() + "" + random.nextInt(1000)));
//        调用Service
        boolean save = hospitalSetService.save(hospitalSet);
        if (save) {

            return Result.ok();
        } else {

            return Result.fail();


        }

    }

    @ApiOperation("根据id获取医院设置")
    @GetMapping("{id}")
    public Result findByIdHospitalSet(@PathVariable("id") Long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return Result.ok(hospitalSet);


    }

    @ApiOperation("修改医院设置")
    @PostMapping("updateHospitalSet")
    public Result updateHospitalSet(@RequestBody HospitalSet hospitalSet) {
        boolean flag = hospitalSetService.updateById(hospitalSet);
        if (flag) {

            return Result.ok();
        } else {

            return Result.fail();


        }

    }

    @ApiOperation("批量删除医院设置")
    @DeleteMapping("batchRemoveHOspitalSet")
    public Result batchRemoveHOspitalSet(@RequestBody List<Integer> idList) {
        boolean flag = hospitalSetService.removeByIds(idList);
        if (flag) {

            return Result.ok();
        } else {

            return Result.fail();


        }

    }

    @ApiOperation("医院设置锁定和解锁")
    @PutMapping("lockHospitalSet/{id}/{status}")
    public Result lockHospitalSet(@PathVariable Long id,
                                  @PathVariable Integer status) {
        //根据id查询医院设置信息
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        //设置状态
        hospitalSet.setStatus(status);
        //调用方法
        hospitalSetService.updateById(hospitalSet);
        return Result.ok();
    }


    @ApiOperation("发送签名秘钥")
    @PutMapping("sendKey/{id}")
    public Result lockHospitalSet(@PathVariable Long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        String signKey = hospitalSet.getSignKey();
        String hoscode = hospitalSet.getHoscode();
        //TODO 发送短信
        return Result.ok();
    }

}

