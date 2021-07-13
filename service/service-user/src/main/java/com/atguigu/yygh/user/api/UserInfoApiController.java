package com.atguigu.yygh.user.api;

import com.atguigu.yygd.common.result.Result;
import com.atguigu.yygd.common.utils.AuthContextHolder;
import com.atguigu.yygh.model.user.UserInfo;
import com.atguigu.yygh.user.service.UserInfoService;
import com.atguigu.yygh.user.utils.SMS;
import com.atguigu.yygh.vo.user.LoginVo;
import com.atguigu.yygh.vo.user.UserAuthVo;
import com.netflix.ribbon.proxy.annotation.Http;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Api(tags="用户信息处理")
@RestController
@RequestMapping("/api/user")
public class UserInfoApiController {
    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation("用户手机号登录接口")
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo){
        Map<String,Object> info = userInfoService.loginUser(loginVo);
        return Result.ok(info);
    }

    @ApiOperation("用户认证接口")
    @PostMapping("auth/userAuth")
    public Result userAuth(@RequestBody UserAuthVo userAuthVo, HttpServletRequest request){
        //传递两个参数

        userInfoService.userAuth(AuthContextHolder.getId(request),userAuthVo);

        return Result.ok();
    }

    @ApiOperation("获取用户id信息,判断是否完成用户认证")
    @GetMapping("auth/getUserInfo")
    public Result getUserInfo(HttpServletRequest request){
        Long id = AuthContextHolder.getId(request);
        UserInfo userInfo = userInfoService.getById(id);
        return Result.ok(userInfo);
    }


}
