package com.atguigu.yygh.sms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.yygh.sms.service.MsmService;
import com.atguigu.yygh.sms.utils.ConstantPropertiesUtils;
import com.atguigu.yygh.vo.msm.MsmVo;
import com.cloopen.rest.sdk.BodyType;
import com.cloopen.rest.sdk.CCPRestSmsSDK;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author zhang
 * @create 2021-07-08 22:10
 */
@Service
public class MsmServiceImpl implements MsmService {
    @Override
    public boolean send(String phone, String code) {
        // 判断手机号是否为空
        if (StringUtils.isEmpty(phone)) {
            return false;
        }
        // 整合容联云
        String serverIp = "app.cloopen.com";
        // 请求端口
        String serverPort = "8883";
        // 主账号,登陆云通讯网站后,可在控制台首页看到开发者主账号ACCOUNT SID和主账号令牌AUTH TOKEN
        String accountSId = ConstantPropertiesUtils.ACCOUNT_SId;
        String accountToken = ConstantPropertiesUtils.ACCOUNT_TOKEN;
        // 请使用管理控制台中已创建应用的APPID
        String appId = ConstantPropertiesUtils.APP_ID;
        System.out.println("accountSId: " + accountSId);
        System.out.println("accountToken: " + accountToken);
        System.out.println("appId: " + appId);
        CCPRestSmsSDK sdk = new CCPRestSmsSDK();
        sdk.init(serverIp, serverPort);
        sdk.setAccount(accountSId, accountToken);
        sdk.setAppId(appId);
        sdk.setBodyType(BodyType.Type_JSON);
        String templateId = "1"; // 模板id
        String[] datas = {code, "2"};
        //HashMap<String, Object> result = sdk.sendTemplateSMS(to,templateId,datas);
        HashMap<String, Object> result = sdk.sendTemplateSMS(phone, templateId, datas);
        return "000000".equals(result.get("statusCode"));
    }

    //mq发送
    private boolean send(String phone, Map<String, Object> param) {
        // 判断手机号是否为空
        if (StringUtils.isEmpty(phone)) {
            return false;
        }
        // 整合容联云
        String serverIp = "app.cloopen.com";
        // 请求端口
        String serverPort = "8883";
        // 主账号,登陆云通讯网站后,可在控制台首页看到开发者主账号ACCOUNT SID和主账号令牌AUTH TOKEN
        String accountSId = ConstantPropertiesUtils.ACCOUNT_SId;
        String accountToken = ConstantPropertiesUtils.ACCOUNT_TOKEN;
        // 请使用管理控制台中已创建应用的APPID
        String appId = ConstantPropertiesUtils.APP_ID;
        System.out.println("accountSId: " + accountSId);
        System.out.println("accountToken: " + accountToken);
        System.out.println("appId: " + appId);
        CCPRestSmsSDK sdk = new CCPRestSmsSDK();
        sdk.init(serverIp, serverPort);
        sdk.setAccount(accountSId, accountToken);
        sdk.setAppId(appId);
        sdk.setBodyType(BodyType.Type_JSON);
        String templateId = "1"; // 模板id
//        String[] datas = {code, "2"};
        //HashMap<String, Object> result = sdk.sendTemplateSMS(to,templateId,datas);
        //短信模板
        String title = JSONObject.toJSONString(param.get("title"));
        String amount = JSONObject.toJSONString(param.get("amount"));
        String reserveDate = JSONObject.toJSONString(param.get("reserveDate"));
        String name = JSONObject.toJSONString(param.get("name"));
        String quitTime = JSONObject.toJSONString(param.get("quitTime"));
        String[] datas = {title,amount,reserveDate,name,quitTime};
        System.out.println("datas"+ Arrays.toString(datas));
        HashMap<String, Object> result = sdk.sendTemplateSMS(phone, templateId, datas);
        return "000000".equals(result.get("statusCode"));
    }

    /**
     * mq发送短信的方法
     * @param msmVo
     * @return
     */
    @Override
    public boolean send(MsmVo msmVo) {
        //发送短信
        if(StringUtils.isEmpty(msmVo.getPhone())){
            return this.send(msmVo.getPhone(), msmVo.getParam());
        }
        return false;
    }
}
