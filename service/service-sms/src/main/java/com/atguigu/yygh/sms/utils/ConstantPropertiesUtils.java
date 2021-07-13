package com.atguigu.yygh.sms.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author zhang
 * @create 2021-07-08 21:39
 */
@Component
public class ConstantPropertiesUtils implements InitializingBean {
    @Value("${accountSId}")
    private String accountSId;
    @Value("${accountToken}")
    private String accountToken;
    @Value("${appId}")
    private String appId;

    public static String ACCOUNT_SId;
    public static String ACCOUNT_TOKEN;
    public static String APP_ID;

    @Override
    public void afterPropertiesSet() throws Exception {
        ACCOUNT_SId = accountSId;
        ACCOUNT_TOKEN = accountToken;
        APP_ID = appId;
    }
}
