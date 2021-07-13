package com.atguigu.yygh.hosp.controller;


import com.atguigu.yygd.common.result.Result;
import com.atguigu.yygh.hosp.service.ScheduleService;
import com.atguigu.yygh.model.hosp.Schedule;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.crypto.KeyGenerator;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "排班规则")
@RequestMapping("/admin/hosp/schedule")

public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    //根据医院编号和科室编号，查询排版规则数据
    @ApiOperation("根据医院编号和科室编号，查询排版规则数据")
    @GetMapping("getSchedule/{page}/{limit}/{hoscode}/{depcode}")
    public Result getScheduleRule(@PathVariable long page,
                                  @PathVariable long limit,
                                  @PathVariable String hoscode,
                                  @PathVariable String depcode) {
        Map<String, Object> map = scheduleService.getRuleSchedule(page, limit, hoscode, depcode);
        return Result.ok(map);
    }

    //根据医院编号、科室编号、工作日期查询排班详细信息
    @ApiOperation("根据医院编号、科室编号、工作日期查询排班详细信息")
    @GetMapping("getScheduleDetail/{hoscode}/{depcode}/{workDate}")
    public Result getScheduleDetail(@PathVariable String hoscode,
                                    @PathVariable String depcode,
                                    @PathVariable String workDate) {
        List<Schedule> list = scheduleService.getDetailSchedule(hoscode, depcode, workDate);
        return Result.ok(list);

    }

}
