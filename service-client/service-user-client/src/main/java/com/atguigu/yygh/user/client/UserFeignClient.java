package com.atguigu.yygh.user.client;

import com.atguigu.yygh.model.user.Patient;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 从注册中心进行远程服务调用
 */
@FeignClient("service-user")
@Repository
public interface UserFeignClient {

    //获取就诊人
    @GetMapping("/api/user/patient/inner/get/{id}")
    Patient getPatientOrder(@PathVariable("id") Long id);
}
