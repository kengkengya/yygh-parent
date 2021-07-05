package com.atguigu.yygh.hosp.controller;


import com.atguigu.yygd.common.result.Result;
import com.atguigu.yygh.hosp.service.DepartmentService;
import com.atguigu.yygh.model.hosp.Department;
import com.atguigu.yygh.vo.hosp.DepartmentVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api("科室信息")
@RequestMapping("/admin/hosp/department")

public class DepartmentController {

    @Autowired
    DepartmentService departmentService;


//    @Cacheable(value = "Depts",keyGenerator = "keyGenerator")
    @ApiOperation("查询医院所有科室列表")
    @GetMapping("getDeptList/{hoscode}")
    public Result getDeptList(@PathVariable String hoscode){

        List<DepartmentVo> depts = departmentService.findDeptByHoscode(hoscode);
        return Result.ok(depts);
    }
}
