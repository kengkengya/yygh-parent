package com.atguigu.yygh.cmn.client;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 从注册中心进行远程服务调用
 */
@FeignClient("service-cmn")
@Repository
public interface DictFeignClient {

    @GetMapping("/admin/cmn/dict/getName/{dictCode}/{value}")
    @ApiOperation("根据dictCode和value查询")
    public String getName(@PathVariable("dictCode") String dictCode, @PathVariable("value") String value);

    @GetMapping("/getName/{value}")
    @ApiOperation("根据value查询")
    public String getName(@PathVariable("value") String value);
}
