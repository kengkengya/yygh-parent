package com.atguigu.yygh.user.utils;

import com.cloopen.rest.sdk.BodyType;
import com.cloopen.rest.sdk.CCPRestSmsSDK;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Set;


public class SMS {
    //主账号,登陆云通讯网站后,可在控制台首页看到开发者主账号ACCOUNT SID和主账号令牌AUTH TOKEN
    public static final String ACCOUNT_SID = "8aaf07087a331dc7017a7b1fe1e41fbc";
    public static final String ACCOUNT_TOKEN = "375ba44d45e3457182041de2f2723ff3";
    public static final String ACCOUNT_APPID = "8aaf07087a331dc7017a7b1fe2f31fc2";

    public static void send(String code){
        //生产环境请求地址：app.cloopen.com
        String serverIp = "app.cloopen.com";
        //请求端口
        String serverPort = "8883";
        //请使用管理控制台中已创建应用的APPID
        //String appId = "appId";
        CCPRestSmsSDK sdk = new CCPRestSmsSDK();
        sdk.init(serverIp, serverPort);
        sdk.setAccount(ACCOUNT_SID, ACCOUNT_TOKEN);
        sdk.setAppId(ACCOUNT_APPID);

        sdk.setBodyType(BodyType.Type_JSON);
        String to = "18801350184";  //绑定的测试手机号
        String templateId= "1";   //短信模板
        String[] datas = {code,"10"};   //分钟参数
        HashMap<String, Object> result = sdk.sendTemplateSMS(to,templateId,datas);
        if("000000".equals(result.get("statusCode"))){
            //正常返回输出data包体信息（map）
            HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for(String key:keySet){
                Object object = data.get(key);
                System.out.println(key +" = "+object);
            }
        }else{
            //异常返回输出错误码和错误信息
            System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
        }
    }

    /**
     * 验证码工具类
     * @return
     */
    public static String generate6BitDigital() {
        return ("" + (Math.random() + 0.1) * 1000000).substring(0, 6);
    }


//    public static void main(String[] args) {
//        //测试
//        String code = "123456";
//        send(code);
//    }
}
