package com.atguigu.yygh.cmn.controller;


import com.atguigu.yygd.common.result.Result;

import com.atguigu.yygh.cmn.service.CmnDictService;
import com.atguigu.yygh.model.cmn.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@Api(tags = "数据字典接口")
@RequestMapping("/admin/cmn/dict")
public class CmnDictController {

    @Autowired
    private CmnDictService dictService;

    @Cacheable(value = "dict",keyGenerator = "keyGenerator")
    @ApiOperation("根据数据id查询下级数据")
    @GetMapping("findChildData/{id}")
    public Result findChildData(@PathVariable Long id){
        List<Dict> childData = dictService.findChildData(id);
        return Result.ok(childData);
    }
    @ApiOperation("导出数据")
    @GetMapping("exportData")
    public Result exportData(HttpServletResponse response){
        dictService.exportData(response);
        return Result.ok();

    }
    @CacheEvict(value = "dict", allEntries=true)
    @ApiOperation("导入数据字典")
    @PostMapping("importDict")
    public Result importDict(MultipartFile file){
        dictService.importDictData(file);
        return Result.ok();

    }

    @GetMapping("getName/{dictCode}/{value}")
    @ApiOperation("根据dictCode和value查询")
    public String getName(@PathVariable String dictCode,@PathVariable String value){
       String name =  dictService.getDictName(dictCode,value);
       return name;
    }

    @GetMapping("getName/{value}")
    @ApiOperation("根据value查询")
    public String getName(@PathVariable String value){
        String name =  dictService.getDictName("",value);
        return name;
    }

    @GetMapping("findByDictCode/{dictCode}")
    @ApiOperation("根据dictcode获取下级节点")
    public Result findByDictCode(@PathVariable String dictCode){
        List<Dict> list = dictService.findByDictCode(dictCode);
        return Result.ok(list);
    }



}
