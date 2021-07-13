package com.atguigu.yygh.sms.service;

import com.atguigu.yygh.vo.msm.MsmVo;

/**
 * @author zhang
 * @create 2021-07-08 22:08
 */
public interface MsmService {
    // 发送收集验证码
    boolean send(String phone, String code);
    //mq使用发送短信的接口
    boolean send(MsmVo msmVo);
}
