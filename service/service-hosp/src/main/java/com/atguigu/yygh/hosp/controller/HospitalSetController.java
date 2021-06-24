package com.atguigu.yygh.hosp.controller;

import com.atguigu.yygd.common.result.Result;
import com.atguigu.yygh.common.utils.MD5;
import com.atguigu.yygh.hosp.service.HospitalSetService;
import com.atguigu.yygh.model.hosp.HospitalSet;
import com.atguigu.yygh.vo.hosp.HospitalSetQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Random;
import java.util.logging.Logger;


@Api(tags = "医院设置管理")
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
@CrossOrigin //跨域注解
public class HospitalSetController {

    @Autowired
    private HospitalSetService hospitalSetService;

    //http://localhost:8201/admin/hosp/hospitalSet/findAll



    /**
     * 查询医院表中所有数据
     * @return
     */
    @ApiOperation(value = "获取所有医院设置")
    @GetMapping("findAll")
    public Result findAllHospital() {
        //调用service方法
        List<HospitalSet> list = hospitalSetService.list();
        return Result.ok(list);
    }

    /**
     * 删除医院设置
     * @param id
     * @return
     */
    @ApiOperation(value = "逻辑删除医院设置")
    @DeleteMapping("{id}")
    public Result removeHospSet(@PathVariable Long id) {
        boolean b = hospitalSetService.removeById(id);
        if (b)
            return Result.ok(b);
        else
            return Result.fail(b);
    }

    /**
     * 条件查询带分页
     * @param current
     * @param limit
     * @param hospitalSetQueryVo
     * @return
     */
    @ApiOperation(value = "条件查询带分页")
    @PostMapping("findPage/{current}/{limit}")
    public Result findPageHospitalSet(@PathVariable long current,
                                      @PathVariable long limit,
                                      @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo) {
        //创造page对象，传递当前页，每页记录数
        Page<HospitalSet> page = new Page<>(current, limit);

        //构建查询
        QueryWrapper<HospitalSet> queryWrapper = new QueryWrapper<>();
        String hosname = hospitalSetQueryVo.getHosname();
        String hoscode = hospitalSetQueryVo.getHoscode();
        if (!StringUtils.isEmpty(hosname))
            queryWrapper.like("hosname", hospitalSetQueryVo.getHosname());
        if (!StringUtils.isEmpty(hoscode))
            queryWrapper.eq("hoscode", hospitalSetQueryVo.getHoscode());

        //调用方法实现分页查询
        Page<HospitalSet> hospitalSetPage = hospitalSetService.page(page, queryWrapper);
        return Result.ok(hospitalSetPage);

    }

    /**
     * 添加医院设置
     * @param hospitalSet
     * @return
     */
    @PostMapping("saveHospitalSet")
    @ApiOperation(value = "添加医院设置")
    public Result saveHospitalSet(@RequestBody HospitalSet hospitalSet) {
        //设置状态1能使用，0不能使用
        hospitalSet.setStatus(1);
        //签名密匙
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis() + "" + random.nextInt(1000)));
        boolean b = hospitalSetService.save(hospitalSet);
        if (b)
            return Result.ok();
        else
            return Result.fail();
    }

    /**
     * 根据id获取医院设置
     * @param id
     * @return
     */
    @GetMapping("getHospSet/{id}")
    @ApiOperation(value = "根据id获取医院设置")
    public Result getHospSet(@PathVariable Long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return Result.ok(hospitalSet);
    }

    /**
     * 修改医院设置
     * @param hospitalSet
     * @return
     */
    @PostMapping("updateHospitalSet")
    @ApiOperation(value = "修改医院设置")
    public Result updateHospitalSet(@RequestBody HospitalSet hospitalSet) {
        boolean b = hospitalSetService.updateById(hospitalSet);
        if (b)
            return Result.ok(b);
        else
            return Result.fail(b);
    }

    /**
     * 批量删除医院设置
     * @param idList
     * @return
     */
    @DeleteMapping("batchRemove")
    @ApiOperation(value = "批量删除医院设置")
    public Result batchRemoveHospitalSet(@RequestBody List<Long> idList) {
        hospitalSetService.removeByIds(idList);
        return Result.ok();
    }

    /**
     * 医院设定的锁定和解锁
     * @param id
     * @param status
     * @return
     */
    @PutMapping("lockHospitalSet/{id}/{status}")
    @ApiOperation(value = "医院设定的锁定和解锁")
    public Result lockHospitalSet(@PathVariable Long id,
                                  @PathVariable Integer status)
    {
        //先查询医院设置信息
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        //设置医院状态
        hospitalSet.setStatus(status);
        //更新医院设置信息
        boolean b = hospitalSetService.updateById(hospitalSet);
        if(b)
            return Result.ok();
        else
            return Result.fail();
    }

    /**
     * 发送签名的密钥
     * @param id
     * @return
     */
    @PutMapping("sendKey/{id}")
    @ApiOperation(value = "发送签名密钥")
    public Result sendKey(@PathVariable Long id){
        //先查询医院设置信息
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        //获取相关内容
        String signKey = hospitalSet.getSignKey();
        String hoscode = hospitalSet.getHoscode();
        return Result.ok();
    }

}
