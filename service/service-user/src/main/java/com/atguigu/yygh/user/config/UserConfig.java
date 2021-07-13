package com.atguigu.yygh.user.config;

import com.atguigu.yygd.common.exception.YyghException;
import com.atguigu.yygd.common.result.ResultCodeEnum;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

@Configuration
@MapperScan("com.atguigu.yygh.user.mapper")
public class UserConfig {

}
