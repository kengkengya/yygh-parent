package com.atguigu.yygh.sms.controller;

import com.atguigu.yygd.common.result.Result;
import com.atguigu.yygh.sms.service.MsmService;
import com.atguigu.yygh.sms.utils.RandomUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author zhang
 * @create 2021-07-08 22:17
 */
@Api("短信接口")
@RestController
@RequestMapping("/api/msm")
public class MsmApiController {
    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 发送手机验证码
    @ApiOperation("发送手机验证码")
    @GetMapping("/send/{phone}")
    public Result sendCode(@PathVariable String phone) {
        // 从 redis 获取验证码，如果获取到，返回ok
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return Result.ok();
        }
        // 如果 redis 获取不到，生成验证码
        code = RandomUtil.getSixBitRandom(); // 获取6位验证码
        boolean isSend = msmService.send(phone, code);
        // 如果发送成功
        if (isSend) {
            // 验证码放到 redis 里面，设置有效时间 2 分钟有效
            redisTemplate.opsForValue().set(phone, code, 2, TimeUnit.MINUTES);
            return Result.ok();
        } else {
            return Result.fail().message("发送短信失败");
        }
    }
}
