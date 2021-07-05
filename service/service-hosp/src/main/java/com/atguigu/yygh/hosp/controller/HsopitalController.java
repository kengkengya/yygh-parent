package com.atguigu.yygh.hosp.controller;

import com.atguigu.yygd.common.result.Result;
import com.atguigu.yygh.hosp.service.HospitalService;
import com.atguigu.yygh.model.hosp.Hospital;
import com.atguigu.yygh.model.hosp.HospitalSet;
import com.atguigu.yygh.vo.hosp.HospitalQueryVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/hosp/hospital")

public class HsopitalController {

    @Autowired
    private HospitalService hospitalService;

    //医院列表(条件查询封装)
    @GetMapping("list/{page}/{limit}")
    public Result listHospital(@PathVariable Integer page, @PathVariable Integer limit,
                               HospitalQueryVo hospitalQueryVo){
        Page<Hospital> pageModel= hospitalService.selectHospitalPage(page,limit,hospitalQueryVo);

        return Result.ok(pageModel);

    }

    //更新医院状态
    @ApiOperation("更新医院状态")
    @GetMapping("updateHospStatus/{id}/{status}")
    public Result updateHospStatus(@PathVariable String id,@PathVariable Integer status){
        hospitalService.updateHospStatus(id,status);
        return Result.ok();
    }
    //医院详情信息
    @ApiOperation("医院详情信息")
    @GetMapping("showHospDetail/{id}")
    public Result showHospitalDetail(@PathVariable String id){
        Map<String,Object> map = hospitalService.getHospitalById(id);
        return Result.ok(map);
    }
}
