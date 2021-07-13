package com.atguigu.yygd.common.utils;

import com.atguigu.yygd.common.helper.JwtHelper;

import javax.servlet.http.HttpServletRequest;

//获取当前用户信息的工具类
public class AuthContextHolder {

    //获取当前用户id
    public static Long getId(HttpServletRequest request){
        //从head里面获取token
        String token = request.getHeader("token");
        //从token里面获取id
        return JwtHelper.getUserId(token);

    }
    //获取当前用户name
    public static String getName(HttpServletRequest request){
        //从head里面获取token
        String token = request.getHeader("token");
        //从token里面获取id
        return JwtHelper.getUserName(token);

    }
}
