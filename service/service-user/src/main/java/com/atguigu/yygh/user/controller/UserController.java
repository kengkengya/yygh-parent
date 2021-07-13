package com.atguigu.yygh.user.controller;

import com.atguigu.yygd.common.result.Result;
import com.atguigu.yygh.model.user.UserInfo;
import com.atguigu.yygh.user.service.UserInfoService;
import com.atguigu.yygh.vo.user.UserInfoQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "平台管理系统")
@RestController
@RequestMapping("/admin/user")
public class UserController {


    @Autowired
    private UserInfoService userInfoService;


    @ApiOperation("用户列表接口")
    @GetMapping("{page}/{limit}")
    public Result list(@PathVariable Long page,
                       @PathVariable long limit,
                       UserInfoQueryVo userInfoQueryVo) {

        Page<UserInfo> pageParam = new Page<>(page, limit);
        IPage<UserInfo> pageModel =
                userInfoService.selectPage(pageParam, userInfoQueryVo);

        return Result.ok(pageModel);
    }

    @ApiOperation("用户锁定")
    @GetMapping("lock/{userId}/{status}")
    public Result lock(@PathVariable Long userId,@PathVariable Integer status){
        userInfoService.lock(userId,status);
        return  Result.ok();
    }

    @ApiOperation("根据用户id，查看用户详情信息")
    @GetMapping("show/{userId}")
    public Result show(@PathVariable Long userId){
        Map<String,Object> map= userInfoService.show(userId);
        return Result.ok(map);
    }

    @ApiOperation("用户审批列表")
    @GetMapping("approval/{userId}/{authStatus}")
    public Result approval(@PathVariable Long userId,@PathVariable Integer authStatus){
        userInfoService.approval(userId,authStatus);
        return Result.ok();
    }
}
